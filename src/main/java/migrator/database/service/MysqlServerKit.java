package migrator.database.service;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;

public class MysqlServerKit implements ServerKit {
    @Override
    public ServerConnection createConnection(Connection connection) {
        return new MysqlConnection(connection);
    }

    @Override
    public ServerConnection createConnection(DatabaseConnection connection) {
        return new MysqlConnection(connection);
    }
}