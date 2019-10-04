package migrator.mock;

import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverFactory;
import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;

public class FakeDatabaseDriverFactory implements DatabaseDriverFactory {
    protected DatabaseDriver driver;

    public FakeDatabaseDriverFactory(DatabaseDriver driver) {
        this.driver = driver;
    }

    @Override
    public DatabaseDriver create(String url, String user, String password) {
        return this.driver;
    }
}