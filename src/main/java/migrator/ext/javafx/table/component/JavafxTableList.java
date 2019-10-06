package migrator.ext.javafx.table.component;

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
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.table.component.TableCard;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.domain.table.service.TableService;
import migrator.app.router.ActiveRoute;
import migrator.app.router.Route;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Subscription;

public class JavafxTableList extends ViewComponent implements TableList {
    protected List<Subscription> subscriptions;
    protected TableService tableService;
    protected DatabaseService databaseService;
    protected TableGuiKit guiKit;
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected ActiveRoute activeRoute;

    @FXML protected FlowPane tables;
    @FXML protected VBox breadcrumpsContainer;

    public JavafxTableList(ViewLoader viewLoader, Container container, Gui gui) {
        super(viewLoader);
        this.activeRoute = container.getActiveRoute();
        this.tableService = container.getTableService();
        this.databaseService = container.getDatabaseService();
        this.guiKit = gui.getTableKit();

        DatabaseConnection connectedDatabaseConnection = this.databaseService.getConnected().get();

        this.breadcrumpsComponent = gui.getBreadcrumps().createBreadcrumps(Arrays.asList(
            new ActiveRouteBreadcrump("Home", new Route("connection.index"), this.activeRoute),
            new ActiveRouteBreadcrump(
                connectedDatabaseConnection.getConnection().getName(),
                new Route("database.index"),
                this.activeRoute
            ),
            new VoidBreadcrump(connectedDatabaseConnection.getDatabase())
        ));
        this.subscriptions = new LinkedList<>();

        this.loadView("/layout/table/index.fxml");

        this.tableService.getList().addListener((Change<? extends Table> change) -> {
            this.draw();
        });
    }

    protected void draw() {
        for (Subscription s : this.subscriptions) {
            s.unsubscribe();
        }
        this.subscriptions.clear();
        this.tables.getChildren().clear();
        Iterator<Table> iterator = this.tableService.getList().iterator();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            TableCard card = this.guiKit.createCard(table);
            this.subscriptions.add(
                card.onSelect((Object o) -> {
                    this.activeRoute.changeTo("tables.view", o);
                })
            );
            this.tables.getChildren().add((Node) card.getContent());
        }
    }

    @FXML
    public void initialize() {
        this.draw();

        this.breadcrumpsContainer.getChildren()
            .setAll(
                (Node) this.breadcrumpsComponent.getContent()
            );
    }

    @FXML public void addTable() {
        Table newTable = this.tableService.getFactory()
            .createWithCreateChange(this.databaseService.getConnected().get(), "new_table");
        this.tableService.register(newTable);
        this.activeRoute.changeTo("table.view", newTable);
    }
}