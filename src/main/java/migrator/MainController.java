package migrator;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import migrator.component.PaneRenderer;
import migrator.javafx.Container;
import migrator.javafx.router.ColumnRoute;
import migrator.javafx.router.ConnectionsRoute;
import migrator.javafx.router.DatabasesRoute;
import migrator.javafx.router.MainRenderer;
import migrator.javafx.router.TableRoute;
import migrator.javafx.router.TableViewRoute;
import migrator.router.Router;

public class MainController implements Initializable {
    @FXML protected VBox centerPane;
    @FXML protected VBox leftPane;
    protected BusinessLogic businessLogic;
    protected Gui gui;
    protected PaneRenderer centePaneRenderer;
    protected PaneRenderer leftPaneRenderer;
    protected Router router;
    protected Container container;

    protected MainController(BusinessLogic businessLogic, Gui gui, Router router) {
        this.businessLogic = businessLogic;
        this.gui = gui;
        this.router = router;
    }

    public MainController(Container container) {
        this(container.getBusinessLogic(), container.getGui(), container.getRouter());
        this.container = container;
    }

    private void registerRoutes() {
        MainRenderer mainRenderer = new MainRenderer(this.centerPane, this.leftPane);

        this.router.connect("connections", new ConnectionsRoute(mainRenderer, this.container));
        this.router.connect("databases", new DatabasesRoute(mainRenderer, this.container));
        this.router.connect("tables", new TableRoute(mainRenderer, this.container));
        this.router.connect("tables.view", new TableViewRoute(mainRenderer, this.container));
        this.router.connect("column", new ColumnRoute(mainRenderer, this.container));
        this.router.show("connections");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.centePaneRenderer = new PaneRenderer(this.centerPane);
        this.leftPaneRenderer = new PaneRenderer(this.leftPane);

        this.registerRoutes();
    }
}