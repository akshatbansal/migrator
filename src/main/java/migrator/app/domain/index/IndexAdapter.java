package migrator.app.domain.index;

import org.json.JSONObject;

import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.repository.UniqueRepository;

public class IndexAdapter implements Adapter<Index, JSONObject> {
    protected UniqueRepository<IndexProperty> indexPropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;

    public IndexAdapter(UniqueRepository<IndexProperty> indexPropertyRepo, UniqueRepository<ChangeCommand> changeCommandRepo) {
        this.indexPropertyRepo = indexPropertyRepo;
        this.changeCommandRepo = changeCommandRepo;
    }

    @Override
    public Index concretize(JSONObject jsonObject) {
        return new Index(
            jsonObject.getString("id"),
            jsonObject.getString("tableId"),
            this.indexPropertyRepo.find(jsonObject.getString("originalId")),
            this.indexPropertyRepo.find(jsonObject.getString("changeId")),
            this.changeCommandRepo.find(jsonObject.getString("changeCommandId"))
        );
    }

    @Override
    public JSONObject generalize(Index item) {
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("id", item.getUniqueKey());
        jsonItem.put("tableId", item.getTableId());
        jsonItem.put("originalId", item.getOriginal().getUniqueKey());
        jsonItem.put("changeId", item.getChange().getUniqueKey());
        jsonItem.put("changeCommandId", item.getChangeCommand().getUniqueKey());
        return jsonItem;
    }
}