package migrator.app.migration.model.table;

import org.json.JSONObject;

import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableProperty;
import migrator.lib.adapter.Adapter;

public class TablePropertyAdapter implements Adapter<TableProperty, JSONObject> {
    @Override
    public TableProperty concretize(JSONObject item) {
        if (item == null) {
            return null;
        }
        return new SimpleTableProperty(
            item.getString("id"),
            item.getString("name")
        );
    }

    @Override
    public JSONObject generalize(TableProperty item) {
        JSONObject json = new JSONObject();
        if (item == null) {
            return json;
        }
        json.put("id", item.getUniqueKey());
        json.put("name", item.getName());
        return json;
    }
}