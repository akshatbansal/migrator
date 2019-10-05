package migrator.ext.phinx;

import migrator.app.code.CodeManager;
import migrator.app.extension.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.app.migration.MigrationConfig;

public class PhinxExtension implements Extension {
    protected CodeManager codeManager;

    public PhinxExtension(CodeManager codeManager) {
        this.codeManager = codeManager;
    }

    @Override
    public void load(ConfigContainer config) {
        MigrationConfig migrationConfig = config.getMigrationConfig();
        migrationConfig.addGeneraotrFactory(
            "phinx",
            new PhinxMigrationGeneratorFactory(this.codeManager)
        );
    }
}