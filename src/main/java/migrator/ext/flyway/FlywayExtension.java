package migrator.ext.flyway;

import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.app.migration.MigrationConfig;

public class FlywayExtension implements Extension {

    @Override
    public void load(ConfigContainer config) {
        MigrationConfig migrationConfig = config.getMigrationConfig();
        migrationConfig.addGeneraotrFactory(
            "flyway",
            new FlywayMigrationGeneratorFactory(
                config.codeManagerConfig().get()
            )
        );
    }
}