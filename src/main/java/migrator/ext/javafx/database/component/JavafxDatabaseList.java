package migrator.ext.javafx.database.component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.ActiveRouteBreadcrump;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.app.breadcrumps.VoidBreadcrump;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.component.DatabaseCard;
import migrator.app.domain.database.component.DatabaseList;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseGuiKit;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.router.ActiveRoute;
import migrator.app.router.Route;
import migrator.lib.emitter.Subscription;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxDatabaseList extends ViewComponent implements DatabaseList {
    protected List<Subscription> subscriptions;
    protected DatabaseService databaseService;
    protected DatabaseGuiKit guiKit;
    protected ActiveRoute activeRoute;
    protected BreadcrumpsComponent breadcrumpsController;

    @FXML protected FlowPane databasesView;
    @FXML protected VBox breadcrumpsContainer;

    public JavafxDatabaseList(ViewLoader viewLoader, Container container, Gui gui) {
        super(viewLoader);
        this.databaseService = container.getDatabaseService();
        this.guiKit = gui.getDatabaseKit();
        this.activeRoute = container.getActiveRoute();
        this.subscriptions = new LinkedList<>();

        Connection connectedConnection = container.getConnectionService().getConnected().get();
        this.breadcrumpsController = gui.getBreadcrumps().createBreadcrumps(Arrays.asList(
            new ActiveRouteBreadcrump(
                "Home", 
                new Route("connection.index"), 
                this.activeRoute
            ),
            new VoidBreadcrump(connectedConnection.getName())
        ));

        this.loadView("/layout/database/index.fxml");

        this.databaseService.getList().addListener((Change<? extends DatabaseConnection> change) -> {
            this.draw();
        });
    }

    protected void draw() {
        for (Subscription s : this.subscriptions) {
            s.unsubscribe();
        }
        this.subscriptions.clear();
        this.databasesView.getChildren().clear();
        Iterator<DatabaseConnection> iterator = this.databaseService.getList().iterator();
        while (iterator.hasNext()) {
            DatabaseConnection connection = iterator.next();
            DatabaseCard card = this.guiKit.createCard(connection);
            this.subscriptions.add(
                card.onConnect((Object o) -> {
                    this.databaseService.connect((DatabaseConnection) o);
                    this.activeRoute.changeTo("table.index", null);
                })
            );
            this.databasesView.getChildren().add((Node) card.getContent());
        }
    }

    @FXML
    public void initialize() {
        this.draw();

        this.breadcrumpsContainer.getChildren()
            .setAll(
                (Node) this.breadcrumpsController.getContent()
            );
    }
}