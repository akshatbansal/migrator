package migrator.database.service;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;

public class DatabaseServerConnectionFactory implements ServerConnectionFactory {
    public ServerConnection createConnection(DatabaseConnection databaseConnection) {
        return this.createMysql(databaseConnection);
    }

    public ServerConnection createConnection(Connection connection) {
        return this.createMysql(connection);
    }

    public MysqlConnection createMysql(String url, String user, String password) {
        return new MysqlConnection(url, user, password);
    }

    public MysqlConnection createMysql(DatabaseConnection databaseConnection) {
        return this.createMysql(databaseConnection.getUrl(), databaseConnection.getConnection().getUser(), databaseConnection.getConnection().getPassword());
    }

    public MysqlConnection createMysql(Connection connection) {
        return this.createMysql(connection.getUrl(), connection.getUser(), connection.getPassword());
    }
}