package migrator.ext.phinx;

import migrator.app.boot.Container;
import migrator.app.extension.Extension;

public class PhinxExtension implements Extension {
    @Override
    public void load(Container container) {
        container.migrationContainer().addGeneratorFactory(
            "phinx",
            new PhinxMigrationGeneratorFactory(
                container.codeContainer()
            )
        );
    }
}