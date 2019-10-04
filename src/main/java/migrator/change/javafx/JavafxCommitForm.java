package migrator.change.javafx;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import migrator.change.component.CommitForm;
import migrator.javafx.controller.ViewController;
import migrator.javafx.helpers.View;
import migrator.migration.ChangeService;
import migrator.migration.MigrationGenerator;
import migrator.migration.TableChange;
import migrator.router.Router;
import migrator.app.migration.Migration;
import migrator.app.migration.MigrationGeneratorFactory;

public class JavafxCommitForm extends ViewController implements CommitForm {
    @FXML protected ComboBox<String> output;
    @FXML protected TextField project;
    protected Router router;
    protected ChangeService changeService;
    protected Migration migration;

    public JavafxCommitForm(View view, Router router, ChangeService changeService, Migration migration) {
        super(view, "/layout/change/commit/form.fxml");
        this.router = router;
        this.changeService = changeService;
        this.migration = migration;
    }

    @Override
    protected void afterInitialize() {
        super.afterInitialize();

        this.output.getItems().setAll(
            this.migration.getGeneratorNames()
        );
    }

    @Override
    public void commit() {
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