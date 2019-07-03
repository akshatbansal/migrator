package migrator.gui.javafx;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.gui.StringInput;

public class JavafxStringInput implements StringInput {
    protected Emitter emitter;
    protected TextField textField;

    public JavafxStringInput(String value) {
        this.emitter = new EventEmitter();
        this.textField = new TextField(value);

        this.textField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.emitter.emit("change", newValue);
        });
    }

    @Override
    public Object getContent() {
        return this.textField;
    }

    @Override
    public String getValue() {
        return this.textField.getText();
    }

    @Override
    public Subscription onChange(Subscriber subscriber) {
        return this.emitter.on("change", subscriber);
    }

    @Override
    public void setValue(String value) {
        this.textField.setText(value);
    }
}