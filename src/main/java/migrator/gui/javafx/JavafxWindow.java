package migrator.gui.javafx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.gui.GuiNode;
import migrator.gui.Window;

public class JavafxWindow implements Window {
    protected Emitter emitter;
    protected Stage stage;
    protected Integer width;
    protected Integer height;

    public JavafxWindow(String title, Integer width, Integer height) {
        this(new Stage(), title, width, height);
    }

    public JavafxWindow(Stage stage, String title, Integer width, Integer height) {
        this.emitter = new EventEmitter();
        this.width = width;
        this.height = height;
        this.stage = stage;

        this.stage.setTitle(title);
        this.stage.setOnHidden((WindowEvent event) -> {
            this.emitter.emit("hide");
        });
        this.stage.setOnShown((WindowEvent event) -> {
            this.emitter.emit("show");
        });
    }

    @Override
    public void hide() {
        this.stage.hide();
    }

    @Override
    public void show() {
        this.stage.show();
    }

    @Override
    public Subscription addOnHide(Subscriber subscriber) {
        return this.emitter.on("hide", subscriber);
    }

    @Override
    public Subscription addOnShow(Subscriber subscriber) {
        return this.emitter.on("show", subscriber);
    }

    @Override
    public void setContent(GuiNode node) {
        Scene scene = new Scene((Parent) node.getContent(), this.width, this.height);
        this.stage.setScene(scene);
    }
}