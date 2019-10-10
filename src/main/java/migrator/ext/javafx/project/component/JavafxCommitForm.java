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

public class JavafxCommitForm extends ViewComponent implements CommitForm {
    @FXML protected TextField name;

    protected ActiveRoute activeRoute;
    protected Migration migration;
    protected Project project;
    protected TableRepository tableRepository;

    public JavafxCommitForm(Project project, ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.activeRoute = container.getActiveRoute();
        this.migration = container.getMigration();
        this.tableRepository = container.getTableRepository();
        this.project = project;
        
        this.loadView("/layout/project/commit/form.fxml");
    }

    @Override
    @FXML public void commit() {
        String outputType = this.project.getOutputType();
        if (outputType == null || outputType.isEmpty()) {
            System.out.println("OUTPUT TYPE IS NOT SELECTED");
            return;
        }
        MigrationGeneratorFactory generatorFactory = this.migration.getGenerator(outputType);
        MigrationGenerator generator = generatorFactory.create();

        List<? extends TableChange> changes = this.tableRepository.getList(this.project.getName());
        generator.generateMigration(this.project.getFolder(), this.name.textProperty().get(), changes);
    }

    @FXML public void close() {
        this.activeRoute.changeTo("table.index");
    }
}