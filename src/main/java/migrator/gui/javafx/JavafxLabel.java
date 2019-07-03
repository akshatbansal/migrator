package migrator.gui.javafx;

import javafx.scene.text.Text;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.gui.Label;

public class JavafxLabel implements Label {
    protected Text text;
    protected Emitter emitter;

    public JavafxLabel(String text) {
        this.emitter = new EventEmitter();
        this.text = new Text(text);
    }

    @Override
    public Object getContent() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text.setText(text);
    }
}