package migrator.ext.postgresql;

import migrator.app.ConfigContainer;
import migrator.app.database.driver.DatabaseDriverConfig;
import migrator.app.extension.Extension;

public class PostgresqlExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        DatabaseDriverConfig databaseDriverConfig = config.getDatabaseDriverConfig();

        databaseDriverConfig.addDriver("postgresql", new PostgresqlDatabaseDriverFactory(
            config.tableFactoryConfig().get(),
            config.columnFactoryConfig().get(),
            config.indexFactoryConfig().get()
        ));
    }
}