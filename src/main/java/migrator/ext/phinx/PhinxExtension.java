package migrator.ext.phinx;

import migrator.app.boot.Container;
import migrator.app.service.Service;

public class PhinxExtension implements Service {
    private Container container;

    public PhinxExtension(Container container) {
        this.container = container;
    }

    @Override
    public void start() {
        container.migrationContainer().addGeneratorFactory(
            "phinx",
            new PhinxMigrationGeneratorFactory(
                container.codeContainer()
            )
        );
    }

    @Override
    public void stop() {
        
    }
}