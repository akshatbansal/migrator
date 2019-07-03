package migrator.database.service;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;

public interface ServerKit {
    public ServerConnection createConnection(Connection connection);
    public ServerConnection createConnection(DatabaseConnection connection);
}