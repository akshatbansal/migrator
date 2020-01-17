package migrator.app.domain.modification;

import migrator.app.boot.Container;
import migrator.app.domain.modification.action.MigrationGenerateHandler;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.service.PersistanceService;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;

public class ModificationService implements Service {
    private PersistanceService<ChangeCommand> changeCommandPersistance;
    private EventDispatcher dispatcher;

    private EventHandler migrationGeneratorHandelr;

    public ModificationService(Container container) {
        this.dispatcher = container.dispatcher();
        this.changeCommandPersistance = new PersistanceService<>(
            container.modificationContainer().repository(),
            container.modificationContainer().storage()
        );

        this.migrationGeneratorHandelr = new MigrationGenerateHandler(
            container.dispatcher(),
            container.migrationContainer(),
            container.tableContainer()
        );
    }

    @Override
    public void start() {
        this.dispatcher.register("commit.generate", this.migrationGeneratorHandelr);

        this.changeCommandPersistance.start();
    }

    @Override
    public void stop() {
        this.dispatcher.unregister("commit.generate", this.migrationGeneratorHandelr);

        this.changeCommandPersistance.stop();
    }
}