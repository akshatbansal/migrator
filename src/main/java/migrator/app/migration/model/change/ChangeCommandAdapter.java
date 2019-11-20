package migrator.app.migration.model.change;

import org.json.JSONObject;

import migrator.app.migration.model.ChangeCommand;
import migrator.lib.adapter.Adapter;

public class ChangeCommandAdapter implements Adapter<ChangeCommand, JSONObject> {
    @Override
    public ChangeCommand concretize(JSONObject item) {
        return new ChangeCommand(
            item.getString("id"),
            item.getString("type")
        );
    }

    @Override
    public JSONObject generalize(ChangeCommand item) {
        JSONObject json = new JSONObject();
        json.put("id", item.getUniqueKey());
        json.put("type", item.getType());
        return json;
    }
}