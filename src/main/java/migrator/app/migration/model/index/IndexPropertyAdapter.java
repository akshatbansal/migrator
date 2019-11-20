package migrator.app.migration.model.index;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import javafx.beans.property.StringProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.lib.adapter.Adapter;

public class IndexPropertyAdapter implements Adapter<IndexProperty, JSONObject> {
    @Override
    public IndexProperty concretize(JSONObject item) {
        return new SimpleIndexProperty(
            item.getString("id"),
            item.getString("name"),
            //item.getString("columns")
            new ArrayList<>()
        );
    }

    @Override
    public JSONObject generalize(IndexProperty item) {
        JSONObject json = new JSONObject();
        json.put("id", item.getUniqueKey());
        json.put("name", item.getName());
        List<String> columnIds = new LinkedList<>();
        for (StringProperty col : item.columnsProperty()) {
            columnIds.add(col.get());
        }
        json.put("columns", columnIds);
        return json;
    }
}