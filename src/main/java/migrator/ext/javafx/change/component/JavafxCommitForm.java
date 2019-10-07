package migrator.ext.javafx.change.component;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.app.Container;
import migrator.app.domain.change.component.CommitForm;
import migrator.app.domain.change.service.ChangeService;
import migrator.app.migration.Migration;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.app.migration.model.TableChange;
import migrator.app.router.ActiveRoute;

public class JavafxCommitForm extends ViewComponent implements CommitForm {
    @FXML protected ComboBox<String> output;
    @FXML protected TextField project;

    protected ActiveRoute activeRoute; 
    protected ChangeService changeService;
    protected Migration migration;

    public JavafxCommitForm(ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.activeRoute = container.getActiveRoute();
        this.changeService = container.getChangeService();
        this.migration = container.getMigration();
        
        this.loadView("/layout/change/commit/form.fxml");

        this.output.getItems().setAll(
            this.migration.getGeneratorNames()
        );
    }

    @Override
    @FXML public void commit() {
        String outputName = this.output.getSelectionModel().getSelectedItem();
        if (outputName == null || outputName.isEmpty()) {
            System.out.println("OUTPUT TYPE IS NOT SELECTED");
            return;
        }
        MigrationGeneratorFactory generatorFactory = this.migration.getGenerator(outputName);
        MigrationGenerator generator = generatorFactory.create();

        List<TableChange> changes = this.changeService.getTables("localhost.ovaldo");
        generator.generateMigration(changes);
    }

    @FXML public void close() {
        this.activeRoute.changeTo("connection.index");
    }
}