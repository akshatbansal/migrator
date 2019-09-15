package migrator.database.service;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;

public interface ServerConnectionFactory {
    public ServerConnection createConnection(DatabaseConnection databaseConnection);
    public ServerConnection createConnection(Connection connection);
}