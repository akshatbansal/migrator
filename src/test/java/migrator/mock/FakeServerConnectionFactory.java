package migrator.mock;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;
import migrator.database.service.ServerConnection;
import migrator.database.service.ServerConnectionFactory;

public class FakeServerConnectionFactory implements ServerConnectionFactory {
    protected ServerConnection serverConnection;

    public FakeServerConnectionFactory(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @Override
    public ServerConnection createConnection(Connection connection) {
        return this.serverConnection;
    }

    @Override
    public ServerConnection createConnection(DatabaseConnection databaseConnection) {
        return this.serverConnection;
    }
}