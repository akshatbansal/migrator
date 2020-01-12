package migrator.ext.flyway;

import migrator.app.boot.Container;
import migrator.app.extension.Extension;

public class FlywayExtension implements Extension {
    @Override
    public void load(Container container) {
        container.migrationContainer().addGeneratorFactory(
            "flyway",
            new FlywayMigrationGeneratorFactory(
                container.codeContainer()
            )
        );
    }
}