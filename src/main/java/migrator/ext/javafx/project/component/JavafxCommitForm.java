package migrator.ext.javafx.project.component;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.TableRepository;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.app.migration.model.TableChange;
import migrator.app.router.ActiveRoute;

public class JavafxCommitForm extends ViewComponent {
    @FXML protected TextField name;

    protected ActiveRoute activeRoute;
    protected ObjectProperty<ProjectContainer> activeProjectContainer;
    protected TableRepository tableRepository;

    public JavafxCommitForm(ObjectProperty<ProjectContainer> activeProjectContainer) {
        super(new ViewLoader());
        // this.activeRoute = container.getActiveRoute();
        // this.migration = container.getMigration();
        // this.tableRepository = container.getTableRepository();
        this.activeProjectContainer = activeProjectContainer;
        
        this.loadView("/layout/project/commit/form.fxml");
    }

    @FXML public void commit() {
        Project project = this.activeProjectContainer.get().getProject();

        String outputType = project.getOutputType();
        if (outputType == null || outputType.isEmpty()) {
            // this.toastService.error("Project output type has to be set");
            return;
        }
        String projectFolder = project.getFolder();
        if (projectFolder == null || projectFolder.isEmpty()) {
            // this.toastService.error("Project folder has to be set");
            return;
        }
        String commitName = name.textProperty().get();
        if (commitName == null || commitName.isEmpty()) {
            // this.toastService.error("Commit name has to be set");
            return;
        }
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

    @FXML public void close() {
        this.activeRoute.changeTo("table.index");
    }
}