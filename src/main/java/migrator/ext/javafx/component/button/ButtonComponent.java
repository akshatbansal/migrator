package migrator.ext.javafx.component.button;

import javafx.scene.control.Button;
import migrator.app.gui.GuiNode;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class ButtonComponent implements GuiNode {
    protected Button button;
    protected Emitter<Void> emitter;

    public ButtonComponent(Button button) {
        this.button = button;
        this.emitter = new EventEmitter<>();

        this.button.setOnAction((e) -> {
            this.emitter.emit("click");
        });
    }

    @Override
    public void destroy() {}

    @Override
    public Object getContent() {
        return this.button;
    }

    public void enable() {
        this.button.setDisable(false);
    }

    public void disable() {
        this.button.setDisable(true);
    }

    public Subscription<Void> onAction(Subscriber<Void> subscriber) {
        return this.emitter.on("click", subscriber);
    }
}