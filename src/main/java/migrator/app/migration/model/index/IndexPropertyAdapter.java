package migrator.app.migration.model.index;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.repository.UniqueRepository;

public class IndexPropertyAdapter implements Adapter<IndexProperty, JSONObject> {
    protected UniqueRepository<ColumnProperty> columnPropertyRepo;

    public IndexPropertyAdapter(UniqueRepository<ColumnProperty> columnPropertyRepo) {
        this.columnPropertyRepo = columnPropertyRepo;
    }

    @Override
    public IndexProperty concretize(JSONObject item) {
        List<ColumnProperty> columns = new ArrayList<>();
        JSONArray columnsJson = item.getJSONArray("columns");
        for (int i = 0; i < columnsJson.length(); i++) {
            String columnId = columnsJson.getString(i);
            if (columnId.isEmpty()) {
                columns.add(null);
                continue;
            }
            columns.add(this.columnPropertyRepo.find(columnId));
        }
        return new SimpleIndexProperty(
            item.getString("id"),
            item.getString("name"),
            columns
        );
    }

    @Override
    public JSONObject generalize(IndexProperty item) {
        JSONObject json = new JSONObject();
        json.put("id", item.getUniqueKey());
        json.put("name", item.getName());
        List<String> columnIds = new LinkedList<>();
        for (ColumnProperty col : item.columnsProperty()) {
            if (col == null) {
                columnIds.add("");
                continue;
            }
            columnIds.add(col.getUniqueKey());
        }
        json.put("columns", columnIds);
        return json;
    }
}