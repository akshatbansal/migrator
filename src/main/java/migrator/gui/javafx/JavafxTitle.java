package migrator.gui.javafx;

import javafx.scene.text.Text;
import migrator.gui.Title;

public class JavafxTitle implements Title {
    protected Text text;

    public JavafxTitle() {
        this("");
    }

    public JavafxTitle(String text) {
        this.text = new Text(text);
    }

    @Override
    public void setText(String value) {
        this.text.setText(value);
    }
}