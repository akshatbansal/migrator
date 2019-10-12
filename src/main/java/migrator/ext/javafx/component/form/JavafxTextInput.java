package migrator.ext.javafx.component.form;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxTextInput extends ViewComponent {
    @FXML protected Text label;
    @FXML protected TextField field;

    protected String labelValue;
    protected StringProperty bindValue;

    public JavafxTextInput(ViewLoader viewLoader, StringProperty bindValue, String label) {
        super(viewLoader);
        this.bindValue = bindValue;
        this.labelValue = label;

        this.loadView("/layout/component/form/textinput.fxml");
    }

    @FXML public void initialize() {
        this.label.textProperty().set(this.labelValue);
        this.field.textProperty().bindBidirectional(
            this.bindValue
        );
    }
}