package migrator.app.domain.project.model;

import org.json.JSONObject;

import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.lib.adapter.Adapter;
import migrator.lib.encryption.Encryption;

public class EncryptedProjectAdapter implements Adapter<Project, JSONObject> {
    @Override
    public Project concretize(JSONObject item) {
        if (item == null) {
            return null;
        }
        return new Project(
            new DatabaseConnection(
                new Connection(
                    item.getString("connection.name"),
                    item.getString("user"),
                    Encryption.decrypt(item.getString("password")),
                    item.getString("host"),
                    item.getString("port"),
                    item.getString("driver")
                ),
                item.getString("database")
            ),
            item.getString("id"),
            item.getString("name"),
            item.getString("output"),
            item.getString("folder")
        );
    }

    @Override
    public JSONObject generalize(Project item) {
        JSONObject json = new JSONObject();
        if (item == null) {
            return json;
        }

        json.put("id", item.getId());
        json.put("name", item.getName());
        json.put("folder", item.getFolder());
        json.put("output", item.getOutputType());
        
        Connection connection = item.getDatabase().getConnection();
        json.put("database", item.getDatabase().getDatabase());
        json.put("driver", connection.getDriver());
        json.put("host", connection.getHost());
        json.put("connection.name", connection.getName());
        json.put("port", connection.getPort());
        json.put("user", connection.getUser());
        json.put("password", Encryption.encrypt(connection.getPassword()));

        return json;
    }
}