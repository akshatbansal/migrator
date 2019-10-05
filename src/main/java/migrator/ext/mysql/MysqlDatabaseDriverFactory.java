package migrator.ext.mysql;

import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverFactory;

public class MysqlDatabaseDriverFactory implements DatabaseDriverFactory {
    @Override
    public DatabaseDriver create(String url, String user, String password) {
        return new MysqlDatabaseDriver(url, user, password);
    }
}