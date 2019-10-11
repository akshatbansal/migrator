package migrator.lib.database.driver;

import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverFactory;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;

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