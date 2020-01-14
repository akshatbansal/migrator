package migrator.app.domain.modification.action;

import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class MigrationGenerateHandler implements EventHandler {
    @Override
    public void handle(Event<?> event) {
        // MigrationGeneratorFactory generatorFactory = this.migration.getGenerator(outputType);
        // MigrationGenerator generator = generatorFactory.create();

        // List<? extends TableChange> changes = this.tableRepository.findByProject(project.getId());
        // if (generator.generateMigration(
        //     project.getFolder(),
        //     this.name.textProperty().get(),
        //     changes
        // )) {
            // this.toastService.success("Migration file crated.");
        // }
    }
}