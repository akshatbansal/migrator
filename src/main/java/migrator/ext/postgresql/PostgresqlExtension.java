package migrator.ext.postgresql;

import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.ext.postgresql.database.PostgresqlStructureFactory;

public class PostgresqlExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        config.databaseContainerConfig().get()
            .addStrucutreFactory(
                "postgresql",
                new PostgresqlStructureFactory(
                    config.loggerConfig()
                )
            );
    }
}