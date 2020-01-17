package migrator.app.gui.component.text;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;

public class PrimaryText extends Text {
    public PrimaryText() {
        this(new SimpleStringProperty(""));
    }

    public PrimaryText(StringProperty text) {
        super();
        this.textProperty().bind(text);
        this.getStyleClass().add("text--primary");
    }
}