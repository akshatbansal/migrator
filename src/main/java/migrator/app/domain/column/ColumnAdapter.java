package migrator.app.domain.column;

import org.json.JSONObject;

import migrator.app.database.format.ColumnFormatManager;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.repository.UniqueRepository;

public class ColumnAdapter implements Adapter<Column, JSONObject> {
    protected ColumnFormatManager columnFormatterManager;
    protected UniqueRepository<ColumnProperty> columnPropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;

    public ColumnAdapter(ColumnFormatManager columnFormatterManager, UniqueRepository<ColumnProperty> columnPropertyRepo, UniqueRepository<ChangeCommand> changeCommandRepo) {
        this.columnFormatterManager = columnFormatterManager;
        this.changeCommandRepo = changeCommandRepo;
        this.columnPropertyRepo = columnPropertyRepo;
    }

    @Override
    public Column concretize(JSONObject item) {
        return new Column(
            this.columnFormatterManager,
            item.getString("id"),
            item.getString("tableId"),
            this.columnPropertyRepo.find(item.getString("originalId")),
            this.columnPropertyRepo.find(item.getString("changeId")),
            this.changeCommandRepo.find(item.getString("changeCommandId"))
        );
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