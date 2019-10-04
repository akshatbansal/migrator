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
import migrator.phpphinx.PhinxMigration;
import migrator.phpphinx.PhpCommandFactory;
import migrator.phpphinx.TimestampFileStorage;
import migrator.phpphinx.command.ConsoleStorage;
import migrator.router.Router;

public class JavafxCommitForm extends ViewController implements CommitForm {
    @FXML protected ComboBox<String> output;
    @FXML protected TextField project;
    protected Router router;
    protected ChangeService changeService;

    public JavafxCommitForm(View view, Router router, ChangeService changeService) {
        super(view, "/layout/change/commit/form.fxml");
        this.router = router;
        this.changeService = changeService;
    }

    @Override
    protected void afterInitialize() {
        super.afterInitialize();

        this.output.getItems().setAll("phinx");
    }

    @Override
    public void commit() {
        MigrationGenerator migrationGenerator = new PhinxMigration(
            new ConsoleStorage(),
            new PhpCommandFactory()
        );
        List<TableChange> changes = this.changeService.getTables("localhost.ovaldo");
        migrationGenerator.generateMigration(changes);
    }

    @FXML public void close() {
        this.router.show("connections");
    }
}