package migrator.ext.javafx;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import migrator.app.gui.component.toast.ToastListComponent;
import migrator.app.gui.service.toast.Toast;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.dispatcher.EventDispatcher;

public class MainController extends ViewComponent {
    protected ToastListComponent toastListComponent;

    @FXML protected VBox centerPane;
    @FXML protected VBox leftPane;
    @FXML protected VBox toastPane;

    public MainController(EventDispatcher dispatcher) {
        super(new ViewLoader());

        this.toastListComponent = new ToastListComponent();
        this.toastListComponent.outputs().addListener((observable, oldValue, newValue) -> {
            dispatcher.dispatch(newValue);
        });

        this.loadView("/layout/main.fxml");

        this.toastPane.getChildren().setAll(
            this.toastListComponent.getNode()
        );
    }

    public void bindToasts(ObservableList<Toast> toasts) {
        this.toastListComponent.bind(toasts);
    }

    public Pane getBodyPane() {
        return this.centerPane;
    }

    public Pane getSidePane() {
        return this.leftPane;
    }
}