package migrator.ext.javafx.component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import migrator.app.toast.Toast;
import migrator.app.toast.ToastService;

public class ToastComponent extends ViewComponent {
    protected Toast toast;
    protected ToastService toastService;

    @FXML protected Text message;

    public ToastComponent(Toast toast, ToastService toastService, ViewLoader viewLoader) {
        super(viewLoader);
        this.toast = toast;
        this.toastService = toastService;

        this.loadView("/layout/toast/toast.fxml");
    }

    @FXML public void initialize() {
        this.message.textProperty().set(this.toast.getMessage());
    }

    public void close() {
        this.toastService.hide(toast);
    }
}