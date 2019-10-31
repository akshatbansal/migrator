package migrator.ext.mariadb;

import migrator.app.ConfigContainer;
import migrator.app.database.driver.DatabaseDriverConfig;
import migrator.app.extension.Extension;
import migrator.ext.mysql.MysqlDatabaseDriverFactory;

public class MariadbExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        DatabaseDriverConfig databaseDriverConfig = config.getDatabaseDriverConfig();

        databaseDriverConfig.addDriver("mariadb", new MysqlDatabaseDriverFactory(
            config.tableFactoryConfig().get(),
            config.columnFactoryConfig().get(),
            config.indexFactoryConfig().get()
        ));
    }
}