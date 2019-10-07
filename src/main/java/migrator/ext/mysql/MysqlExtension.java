package migrator.ext.mysql;

import migrator.app.database.driver.DatabaseDriverConfig;
import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;

public class MysqlExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        DatabaseDriverConfig databaseDriverConfig = config.getDatabaseDriverConfig();

        databaseDriverConfig.addDriver("mysql", new MysqlDatabaseDriverFactory());
    }
}