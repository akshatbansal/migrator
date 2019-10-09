package migrator.ext.javafx.table.component;

import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.domain.table.service.TableService;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.CardListComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Subscription;

public class JavafxTableList extends ViewComponent implements TableList {
    protected List<Subscription<Table>> subscriptions;
    protected TableService tableService;
    protected ProjectService projectService;
    protected TableGuiKit guiKit;
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected ActiveRoute activeRoute;
    protected CardListComponent<Table> cardListComponent;

    @FXML protected FlowPane tables;
    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox tableCards;

    public JavafxTableList(ViewLoader viewLoader, Container container, Gui gui) {
        super(viewLoader);
        this.activeRoute = container.getActiveRoute();
        this.tableService = container.getTableService();
        this.projectService = container.getProjectService();
        this.guiKit = gui.getTableKit();

        this.breadcrumpsComponent = gui.getBreadcrumps().createBreadcrumps(
            this.projectService.getOpened().get()
        );
        this.subscriptions = new LinkedList<>();

        this.cardListComponent = new CardListComponent<>(
            this.tableService.getAll(),
            new TableCardFactory(), 
            viewLoader
        );

        this.cardListComponent.onPrimary((Table eventTable) -> {
            this.tableService.activate(eventTable);
            this.activeRoute.changeTo("table.view", eventTable);
        });

        this.loadView("/layout/table/index.fxml");
    }

    @FXML public void initialize() {
        this.tableCards.getChildren()
            .setAll(
                (Node) this.cardListComponent.getContent()
            );

        this.breadcrumpsContainer.getChildren()
            .setAll(
                (Node) this.breadcrumpsComponent.getContent()
            );
    }

    @Override
    @FXML public void addTable() {
        Table newTable = this.tableService.getFactory()
            .createWithCreateChange(this.projectService.getOpened().get(), "new_table");
        this.tableService.addAndActivate(newTable);
        this.activeRoute.changeTo("table.view", newTable);
    }

    @Override
    @FXML public void commit() {
        this.activeRoute.changeTo("commit.view", this.projectService.getOpened().get());
    }
}