package migrator;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import migrator.component.PaneRenderer;
import migrator.app.Container;
import migrator.app.Gui;

public class MainController implements Initializable {
    @FXML protected VBox centerPane;
    @FXML protected VBox leftPane;

    protected Gui gui;
    protected PaneRenderer centePaneRenderer;
    protected PaneRenderer leftPaneRenderer;
    protected Container container;

    protected MainController(Container container, Gui gui) {
        this.container = container;
        this.gui = gui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.centePaneRenderer = new PaneRenderer(this.centerPane);
        this.leftPaneRenderer = new PaneRenderer(this.leftPane);
    }

    public Pane getBodyPane() {
        return this.centerPane;
    }

    public Pane getSidePane() {
        return this.leftPane;
    }
}