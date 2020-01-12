package migrator.app.domain.modification;

import migrator.app.boot.Container;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.service.PersistanceService;
import migrator.app.service.Service;

public class ModificationService implements Service {
    private PersistanceService<ChangeCommand> changeCommandPersistance;   

    public ModificationService(Container container) {
        this.changeCommandPersistance = new PersistanceService<>(
            container.modificationContainer().repository(),
            container.modificationContainer().storage()
        );
    }

    @Override
    public void start() {
        this.changeCommandPersistance.start();
    }

    @Override
    public void stop() {
        this.changeCommandPersistance.stop();
    }
}