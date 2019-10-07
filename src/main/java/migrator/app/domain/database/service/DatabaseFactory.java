package migrator.app.domain.database.service;

import migrator.app.domain.connection.service.ConnectionFactory;
import migrator.app.domain.database.model.DatabaseConnection;

public class DatabaseFactory {
    protected ConnectionFactory connectionFactory;

    public DatabaseFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public DatabaseConnection create(String connectionName) {
        return new DatabaseConnection(
            this.connectionFactory.create(connectionName),
            "db_name"
        );
    }
}