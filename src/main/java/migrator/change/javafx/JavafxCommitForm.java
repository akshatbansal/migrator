package migrator.change.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import migrator.change.component.CommitForm;
import migrator.javafx.controller.ViewController;
import migrator.javafx.helpers.View;

public class JavafxCommitForm extends ViewController implements CommitForm {
    @FXML protected ComboBox<String> output;
    @FXML protected TextField project;

    public JavafxCommitForm(View view) {
        super(view, "/layout/change/commit/form.fxml");
    }

    @Override
    protected void afterInitialize() {
        super.afterInitialize();

        this.output.getItems().setAll("phinx");
    }

    @Override
    public void commit() {
        System.out.println("COMMIT");
    }
}