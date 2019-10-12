package migrator.ext.javafx.toast;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import migrator.app.toast.Toast;
import migrator.app.toast.ToastService;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class ToastComponent extends ViewComponent {
    protected Toast toast;
    protected ToastService toastService;

    @FXML protected Text message;
    @FXML protected HBox pane;

    public ToastComponent(Toast toast, ToastService toastService, ViewLoader viewLoader) {
        super(viewLoader);
        this.toast = toast;
        this.toastService = toastService;

        this.loadView("/layout/toast/toast.fxml");
    }

    @FXML public void initialize() {
        this.message.textProperty().set(this.toast.getMessage());
        this.pane.getStyleClass().add("toast--" + this.toast.getType());
    }

    public void close() {
        this.toastService.hide(toast);
    }
}