package migrator.database.javafx;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import migrator.breadcrumps.BreadcrumpsComponent;
import migrator.database.component.DatabaseCard;
import migrator.database.component.DatabaseList;
import migrator.database.model.DatabaseConnection;
import migrator.database.service.DatabaseService;
import migrator.database.service.GuiKit;
import migrator.emitter.Subscription;
import migrator.javafx.helpers.ControllerHelper;
import migrator.router.Router;

public class JavafxDatabaseList implements DatabaseList {
    protected Node node;
    protected List<Subscription> subscriptions;
    protected DatabaseService databaseService;
    protected GuiKit guiKit;
    protected Router router;
    protected BreadcrumpsComponent breadcrumpsController;

    @FXML protected FlowPane databasesView;
    @FXML protected VBox breadcrumpsContainer;

    public JavafxDatabaseList(DatabaseService databaseService, GuiKit guiKit, Router router, migrator.breadcrumps.GuiKit breadcrumpsGuiKit) {
        this.databaseService = databaseService;
        this.guiKit = guiKit;
        this.router = router;
        this.subscriptions = new LinkedList<>();
        this.breadcrumpsController = breadcrumpsGuiKit.createBreadcrumps();
        this.node = ControllerHelper.createViewNode(this, "/layout/database/index.fxml");

        this.databaseService.getList().addListener((Change<? extends DatabaseConnection> change) -> {
            this.draw();
        });
    }

    @Override
    public Object getContent() {
        return this.node;
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
                    this.router.show("tables", o);
                })
            );
            this.databasesView.getChildren().add((Node) card.getContent());
        }
    }

    @FXML
    public void initialize() {
        this.draw();
        ControllerHelper.replaceNode(this.breadcrumpsContainer, this.breadcrumpsController);
    }
}