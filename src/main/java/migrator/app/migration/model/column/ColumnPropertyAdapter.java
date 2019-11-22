package migrator.app.migration.model.column;

import org.json.JSONObject;

import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.lib.adapter.Adapter;

public class ColumnPropertyAdapter implements Adapter<ColumnProperty, JSONObject> {
    @Override
    public ColumnProperty concretize(JSONObject json) {
        if (json == null) {
            return null;
        }
        return new SimpleColumnProperty(
            json.getString("id"),
            json.getString("name"),
            json.getString("format"),
            json.getString("defaultValue"),
            json.getBoolean("nullEnabled"),
            json.getString("length"),
            json.getBoolean("sign"),
            json.getString("precision"),
            json.getBoolean("autoIncrement")
        );
    }

    @Override
    public JSONObject generalize(ColumnProperty item) {
        JSONObject json = new JSONObject();
        if (item == null) {
            return json;
        }
        json.put("id", item.getUniqueKey());
        json.put("name", item.getName());
        json.put("format", item.getFormat());
        json.put("nullEnabled", item.isNullEnabled());
        json.put("defaultValue", item.getDefaultValue());
        json.put("length", item.getLength());
        json.put("precision", item.getPrecision());
        json.put("sign", item.isSigned());
        json.put("autoIncrement", item.isAutoIncrement());
        return json;
    }
}