package migrator.app.gui.component.text;

import javafx.scene.text.Text;

public class WhiteText extends Text {
    public WhiteText() {
        this("");
    }

    public WhiteText(String text) {
        super(text);
        this.getStyleClass().add("text--white");
    }
}