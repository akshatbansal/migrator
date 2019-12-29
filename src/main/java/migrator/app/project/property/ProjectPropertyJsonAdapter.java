package migrator.app.project.property;

import org.json.JSONObject;

import migrator.lib.adapter.Adapter;

public class ProjectPropertyJsonAdapter implements Adapter<ProjectProperty, JSONObject> {
    @Override
    public ProjectProperty concretize(JSONObject item) {
        if (item == null) {
            return null;
        }

        return new SimpleProjectProperty(
            item.getString("id"),
            item.getString("name"),
            item.getString("folder"),
            item.getString("output"),
            new SimpleDatabaseProperty(
                item.getString("database.url"),
                item.getString("database.user"),
                item.getString("database.password"),
                item.getString("database.driver")
            )
        );
    }

    @Override
    public JSONObject generalize(ProjectProperty item) {
        JSONObject json = new JSONObject();
        if (item == null) {
            return json;
        }

        json.put("id", item.getUniqueKey());
        json.put("name", item.nameProperty().get());
        json.put("folder", item.folderProperty().get());
        json.put("output", item.outputProperty().get());

        json.put("database.driver", item.getDatabase().driverProperty().get());
        json.put("database.password", item.getDatabase().passwordProperty().get());
        json.put("database.user", item.getDatabase().userProperty().get());
        json.put("database.url", item.getDatabase().urlProperty().get());

        return json;
    }
}