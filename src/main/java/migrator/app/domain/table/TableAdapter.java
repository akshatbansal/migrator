package migrator.app.domain.table;

import org.json.JSONObject;

import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.index.IndexRepository;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.TableProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.repository.UniqueRepository;

public class TableAdapter implements Adapter<Table, JSONObject> {
    protected UniqueRepository<TableProperty> tablePropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;
    protected ColumnRepository columnRepo;
    protected IndexRepository indexRepo;

    public TableAdapter(
        UniqueRepository<TableProperty> tablePropertyRepo,
        UniqueRepository<ChangeCommand> changeCommandRepo,
        ColumnRepository columnRepo,
        IndexRepository indexRepo
    ) {
        this.tablePropertyRepo = tablePropertyRepo;
        this.changeCommandRepo = changeCommandRepo;
        this.indexRepo = indexRepo;
        this.columnRepo = columnRepo;
    }

    @Override
    public Table concretize(JSONObject json) {
        if (json == null) {
            return null;
        }
        String tableId = json.getString("id");
        return new Table(
            tableId,
            json.getString("projectId"),
            this.tablePropertyRepo.find(
                json.getString("originalId")
            ),
            this.tablePropertyRepo.find(
                json.getString("changeId")
            ),
            this.changeCommandRepo.find(
                json.getString("changeCommandId")
            ),
            this.columnRepo.byTableProperty(tableId),
            this.indexRepo.byTableProperty(tableId)
        );
    }

    @Override
    public JSONObject generalize(Table item) {
        JSONObject json = new JSONObject();
        if (item == null) {
            return json;
        }
        json.put("id", item.getUniqueKey());
        json.put("projectId", item.getProjectId());
        json.put("originalId", item.getOriginal().getUniqueKey());
        json.put("changeId", item.getChange().getUniqueKey());
        json.put("changeCommandId", item.getChangeCommand().getUniqueKey());
        return json;
    }
}