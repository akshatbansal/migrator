package migrator.app.database.driver;

import java.util.Collection;

import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;

public class DatabaseDriverManager {
    protected DatabaseDriverConfig config;

    public DatabaseDriverManager(DatabaseDriverConfig config) {
        this.config = config;
    }

    public Collection<String> getDriverNames() {
        return this.config.getDrivers().keySet();
    }

    public DatabaseDriverFactory getDriverFactory(String name) {
        return this.config.getDrivers().get(name);
    }

    public DatabaseDriver createDriver(Connection connection) {
        DatabaseDriverFactory factory = this.getDriverFactory(connection.getDriver());
        return factory.create(connection.getUrl(), connection.getUser(), connection.getPassword());
    }

    public DatabaseDriver createDriver(DatabaseConnection databaseConnection) {
        Connection connection = databaseConnection.getConnection();
        DatabaseDriverFactory factory = this.getDriverFactory(connection.getDriver());
        return factory.create(databaseConnection.getUrl(), connection.getUser(), connection.getPassword());
    }
}