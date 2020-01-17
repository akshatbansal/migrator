package migrator.app.domain.modification.action;

import java.util.List;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.TableContainer;
import migrator.app.gui.service.toast.Toast;
import migrator.app.migration.MigrationContainer;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.app.migration.model.TableChange;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;
import migrator.lib.result.BooleanResult;

public class MigrationGenerateHandler implements EventHandler {
    private MigrationContainer migrationContainer;
    private EventDispatcher dispatcher;
    private TableContainer tableContainer;
    
    public MigrationGenerateHandler(
        EventDispatcher dispatcher,
        MigrationContainer migrationContainer,
        TableContainer tableContainer
    ) {
        this.dispatcher = dispatcher;
        this.migrationContainer = migrationContainer;
        this.tableContainer = tableContainer;
    }

    @Override
    public void handle(Event<?> event) {
        CommitEventData eventData = (CommitEventData) event.getValue();
        if (eventData == null) {
            return;
        }

        Project project = eventData.getProject();
        MigrationGeneratorFactory generatorFactory = this.migrationContainer.getGeneratorFactoryFor(project.getOutputType());
        MigrationGenerator generator = generatorFactory.create();

        List<? extends TableChange> changes = this.tableContainer.tableRepository().findByProject(project.getId());
        BooleanResult result = generator.generateMigration(
            project.getFolder(),
            eventData.getCommitName(),
            changes
        );
        if (result.isOk()) {
            this.dispatcher.dispatch(
                new SimpleEvent<>("toast.push", new Toast("Migration file crated.", "success"))
            );
        } else {
            this.dispatcher.dispatch(
                new SimpleEvent<>("toast.push", new Toast(result.getError(), "error"))
            );
        }
    }
}