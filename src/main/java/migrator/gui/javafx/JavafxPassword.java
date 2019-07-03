package migrator.gui.javafx;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.gui.Password;

public class JavafxPassword implements Password {
    protected Emitter emitter;
    protected PasswordField password;

    public JavafxPassword(String value) {
        this.emitter = new EventEmitter();
        this.password = new PasswordField();

        this.password.textProperty().addListener((observable, oldValue, newValue) -> {
            this.emitter.emit("change", newValue);
        });
    }

    @Override
    public Object getContent() {
        return this.password;
    }

    @Override
    public String getValue() {
        return this.password.getText();
    }

    @Override
    public Subscription onChange(Subscriber subscriber) {
        return this.emitter.on("change", subscriber);
    }

    @Override
    public void setValue(String value) {
        this.password.setText(value);
    }
}