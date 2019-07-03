package migrator.gui.javafx;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.gui.PrimaryButton;

public class JavafxPrimaryButton implements PrimaryButton {
    protected Button button;
    protected Emitter emitter;

    public JavafxPrimaryButton() {
        this("");
    }

    public JavafxPrimaryButton(String text) {
        this.emitter = new EventEmitter();
        this.button = new Button(text);

        this.button.setOnAction((ActionEvent event) -> {
            this.emitter.emit("action", event);
        });
    }

    @Override
    public Subscription onAction(Subscriber subscriber) {
        return this.emitter.on("action", subscriber);
    }

    @Override
    public void setText(String value) {
        this.button.setText(value);
    }

    @Override
    public Object getContent() {
        return this.button;
    }
}