package migrator.ext.phinx;

import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.app.migration.MigrationConfig;

public class PhinxExtension implements Extension {
    @Override
    public void load(ConfigContainer config) {
        MigrationConfig migrationConfig = config.getMigrationConfig();
        migrationConfig.addGeneraotrFactory(
            "phinx",
            new PhinxMigrationGeneratorFactory(config.codeManagerConfig().get())
        );
    }
}