package migrator.table.javafx;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import migrator.app.domain.database.service.DatabaseService;
import migrator.breadcrumps.BreadcrumpsComponent;
import migrator.emitter.Subscription;
import migrator.javafx.helpers.ControllerHelper;
import migrator.router.Router;
import migrator.table.component.TableCard;
import migrator.table.component.TableList;
import migrator.table.model.Table;
import migrator.table.service.GuiKit;
import migrator.table.service.TableService;

public class JavafxTableList implements TableList {
    protected Node node;
    protected List<Subscription> subscriptions;
    protected TableService tableService;
    protected DatabaseService databaseService;
    protected GuiKit guiKit;
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected Router router;
    @FXML protected FlowPane tables;
    @FXML protected VBox breadcrumpsContainer;

    public JavafxTableList(TableService tableService, DatabaseService databaseService,  GuiKit guiKit, migrator.breadcrumps.GuiKit breadcrumpsGuiKit, Router router) {
        this.tableService = tableService;
        this.databaseService = databaseService;
        this.guiKit = guiKit;
        this.breadcrumpsComponent = breadcrumpsGuiKit.createBreadcrumps();
        this.router = router;
        this.subscriptions = new LinkedList<>();
        this.node = ControllerHelper.createViewNode(this, "/layout/table/index.fxml");

        this.tableService.getList().addListener((Change<? extends Table> change) -> {
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
        this.tables.getChildren().clear();
        Iterator<Table> iterator = this.tableService.getList().iterator();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            TableCard card = this.guiKit.createCard(table);
            this.subscriptions.add(
                card.onSelect((Object o) -> {
                    this.router.show("tables.view", o);
                })
            );
            this.tables.getChildren().add((Node) card.getContent());
        }
    }

    @FXML
    public void initialize() {
        this.draw();
        ControllerHelper.replaceNode(this.breadcrumpsContainer, this.breadcrumpsComponent);
    }

    @FXML public void addTable() {
        Table newTable = this.tableService.getFactory()
            .createWithCreateChange(this.databaseService.getConnected().get(), "new_table");
        this.tableService.register(newTable);
        this.router.show("tables.view", newTable);
    }
}