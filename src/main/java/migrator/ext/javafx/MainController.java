package migrator.ext.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class MainController extends ViewComponent implements Initializable {
    @FXML protected VBox centerPane;
    @FXML protected VBox leftPane;

    public MainController(ViewLoader viewLoader) {
        super(viewLoader);

        this.loadView("/layout/main.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Pane getBodyPane() {
        return this.centerPane;
    }

    public Pane getSidePane() {
        return this.leftPane;
    }
}