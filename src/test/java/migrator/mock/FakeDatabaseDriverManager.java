package migrator.mock;

import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverConfig;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;

public class FakeDatabaseDriverManager extends DatabaseDriverManager {
    protected DatabaseDriver driver;

    public FakeDatabaseDriverManager(DatabaseDriver driver) {
        super(new DatabaseDriverConfig());
        this.driver = driver;
    }
    @Override
    public DatabaseDriver createDriver(Connection connection) {
        return this.driver;
    }

    @Override
    public DatabaseDriver createDriver(DatabaseConnection databaseConnection) {
        return this.driver;
    }
}