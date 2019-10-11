package migrator.ext.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.ext.javafx.toast.ToastListComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class MainController extends ViewComponent implements Initializable {
    protected ToastListComponent toastListComponent;

    @FXML protected VBox centerPane;
    @FXML protected VBox leftPane;
    @FXML protected VBox toastPane;

    public MainController(ViewLoader viewLoader, Container container) {
        super(viewLoader);

        this.toastListComponent = new ToastListComponent(
            container.getToastService(),
            viewLoader
        );

        this.loadView("/layout/main.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.toastPane.getChildren().setAll(
            (Node) this.toastListComponent.getContent()
        );
    }

    public Pane getBodyPane() {
        return this.centerPane;
    }

    public Pane getSidePane() {
        return this.leftPane;
    }
}