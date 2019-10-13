package migrator.ext.javafx.component.form;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxCheckbox extends ViewComponent {
    @FXML protected Text label;
    @FXML protected CheckBox field;

    protected String labelValue;
    protected Property<Boolean> bindValue;

    public JavafxCheckbox(ViewLoader viewLoader, Property<Boolean> bindValue, String label) {
        super(viewLoader);
        this.bindValue = bindValue;
        this.labelValue = label;

        this.loadView("/layout/component/form/checkbox.fxml");
    }

    @FXML public void initialize() {
        this.label.textProperty().set(this.labelValue);
        this.field.selectedProperty().bindBidirectional(
            this.bindValue
        );
    }
}