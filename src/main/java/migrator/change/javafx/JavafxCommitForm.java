package migrator.change.javafx;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import migrator.change.component.CommitForm;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.migration.ChangeService;
import migrator.migration.MigrationGenerator;
import migrator.migration.TableChange;
import migrator.router.Router;
import migrator.app.migration.Migration;
import migrator.app.migration.MigrationGeneratorFactory;

public class JavafxCommitForm extends ViewComponent implements CommitForm {
    @FXML protected ComboBox<String> output;
    @FXML protected TextField project;
    protected Router router;
    protected ChangeService changeService;
    protected Migration migration;

    public JavafxCommitForm(ViewLoader viewLoader, Router router, ChangeService changeService, Migration migration) {
        super(viewLoader);
        this.router = router;
        this.changeService = changeService;
        this.migration = migration;
        
        this.loadView("/layout/change/commit/form.fxml");

        this.output.getItems().setAll(
            this.migration.getGeneratorNames()
        );
    }

    @Override
    @FXML public void commit() {
        System.out.println("COMMIT");
        String outputName = this.output.getSelectionModel().getSelectedItem();
        if (outputName == null || outputName.isEmpty()) {
            System.out.println("EMPTY OUTPUT");
            return;
        }
        MigrationGeneratorFactory generatorFactory = this.migration.getGenerator(outputName);
        MigrationGenerator generator = generatorFactory.create();

        List<TableChange> changes = this.changeService.getTables("localhost.ovaldo");
        generator.generateMigration(changes);
    }

    @FXML public void close() {
        this.router.show("connections");
    }
}