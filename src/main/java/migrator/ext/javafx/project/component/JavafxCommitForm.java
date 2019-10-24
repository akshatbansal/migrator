package migrator.ext.javafx.project.component;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.app.Container;
import migrator.app.domain.project.component.CommitForm;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.service.TableRepository;
import migrator.app.migration.Migration;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.app.migration.model.TableChange;
import migrator.app.router.ActiveRoute;
import migrator.app.toast.ToastService;

public class JavafxCommitForm extends ViewComponent implements CommitForm {
    @FXML protected TextField name;

    protected ActiveRoute activeRoute;
    protected Migration migration;
    protected Project project;
    protected TableRepository tableRepository;
    protected ToastService toastService;

    public JavafxCommitForm(Project project, ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.activeRoute = container.getActiveRoute();
        this.migration = container.getMigration();
        this.tableRepository = container.getTableRepository();
        this.toastService = container.getToastService();
        this.project = project;
        
        this.loadView("/layout/project/commit/form.fxml");
    }

    @Override
    @FXML public void commit() {
        String outputType = this.project.getOutputType();
        if (outputType == null || outputType.isEmpty()) {
            this.toastService.error("Project output type has to be set");
            return;
        }
        String projectFolder = this.project.getFolder();
        if (projectFolder == null || projectFolder.isEmpty()) {
            this.toastService.error("Project folder has to be set");
            return;
        }
        String commitName = this.name.textProperty().get();
        if (commitName == null || commitName.isEmpty()) {
            this.toastService.error("Commit name has to be set");
            return;
        }
        MigrationGeneratorFactory generatorFactory = this.migration.getGenerator(outputType);
        MigrationGenerator generator = generatorFactory.create();

        List<? extends TableChange> changes = this.tableRepository.getList(this.project.getId());
        generator.generateMigration(this.project.getFolder(), this.name.textProperty().get(), changes);
        this.toastService.success("Migration file crated.");
    }

    @FXML public void close() {
        this.activeRoute.changeTo("table.index");
    }
}