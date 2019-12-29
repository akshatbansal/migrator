package migrator.app.domain.column;

import org.json.JSONObject;

import migrator.app.domain.table.model.Column;
import migrator.app.gui.column.format.ColumnFormat;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.repository.UniqueRepository;

public class ColumnAdapter implements Adapter<Column, JSONObject> {
    protected UniqueRepository<ColumnProperty> columnPropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;
    protected ColumnFormatCollection columnFormatCollection;

    public ColumnAdapter(UniqueRepository<ColumnProperty> columnPropertyRepo, UniqueRepository<ChangeCommand> changeCommandRepo, ColumnFormatCollection columnFormatCollection) {
        this.changeCommandRepo = changeCommandRepo;
        this.columnPropertyRepo = columnPropertyRepo;
        this.columnFormatCollection = columnFormatCollection;
    }

    @Override
    public Column concretize(JSONObject item) {
        Column column = new Column(
            item.getString("id"),
            item.getString("tableId"),
            this.columnPropertyRepo.find(item.getString("originalId")),
            this.columnPropertyRepo.find(item.getString("changeId")),
            this.changeCommandRepo.find(item.getString("changeCommandId"))
        );
        ColumnFormat columnFormat = this.columnFormatCollection.getFormatByName(column.getFormat());
        columnFormat.updateModel(column);

        return column;
    }

    @Override
    public JSONObject generalize(Column item) {
        JSONObject json = new JSONObject();
        json.put("id", item.getUniqueKey());
        json.put("tableId", item.getTableId());
        json.put("originalId", item.getOriginal().getUniqueKey());
        json.put("changeId", item.getChange().getUniqueKey());
        json.put("changeCommandId", item.getChangeCommand().getUniqueKey());
        return json;
    }
}