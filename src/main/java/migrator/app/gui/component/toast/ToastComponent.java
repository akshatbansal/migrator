package migrator.app.gui.component.toast;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.service.toast.Toast;
import migrator.lib.dispatcher.SimpleEvent;

public class ToastComponent extends SimpleComponent implements Component {
    protected Toast toast;

    @FXML protected Label message;
    @FXML protected VBox pane;

    public ToastComponent() {
        super();

        this.loadFxml("/layout/toast/toast.fxml");
        this.message.wrapTextProperty().set(true);
        ;
    }

    public void bind(Toast toast) {
        this.toast = toast;
        this.message.textProperty().set(this.toast.getMessage());
        this.pane.getStyleClass().add("toast--" + this.toast.getType());
    }

    public void close() {
        this.events.set(new SimpleEvent<Toast>("toast.close", this.toast));
    }
}