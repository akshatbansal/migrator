package migrator.ext.flyway;

import migrator.app.boot.Container;
import migrator.app.service.Service;

public class FlywayExtension implements Service {
    private Container container;

    public FlywayExtension(Container container) {
        this.container = container;
    }

    @Override
    public void start() {
        container.migrationContainer().addGeneratorFactory(
            "flyway",
            new FlywayMigrationGeneratorFactory(
                container.codeContainer()
            )
        );
    }

    @Override
    public void stop() {
        
    }
}