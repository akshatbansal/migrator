package migrator.ext.javafx.table.component;

import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.index.service.IndexActiveState;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.card.CardListComponent;
import migrator.ext.javafx.component.card.withmarks.CardWithMarksComponentFactory;
import migrator.ext.javafx.component.SearchComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Subscription;
import migrator.lib.hotkyes.Hotkey;

public class JavafxTableList extends ViewComponent implements TableList {
    protected TableFactory tableFactory;
    protected TableActiveState tableActiveState;
    protected ProjectService projectService;
    protected TableGuiKit guiKit;
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected ActiveRoute activeRoute;
    protected CardListComponent<Table> cardListComponent;
    protected SearchComponent searchComponent;
    protected ColumnActiveState columnActiveState;
    protected ColumnFactory columnFactory;
    protected IndexActiveState indexActiveState;
    protected IndexFactory indexFactory;

    @FXML protected FlowPane tables;
    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox tableCards;
    @FXML protected VBox searchBox;

    public JavafxTableList(ViewLoader viewLoader, Container container, Gui gui) {
        super(viewLoader);
        this.activeRoute = container.getActiveRoute();
        this.tableFactory = container.getTableFactory();
        this.tableActiveState = container.getTableActiveState();
        this.projectService = container.getProjectService();
        this.guiKit = gui.getTableKit();
        this.columnActiveState = container.getColumnActiveState();
        this.columnFactory = container.getColumnFactory();
        this.indexActiveState = container.getIndexActiveState();
        this.indexFactory = container.getIndexFactory();

        this.breadcrumpsComponent = gui.getBreadcrumps().createBreadcrumps(
            this.projectService.getOpened().get()
        );

        this.cardListComponent = new CardListComponent<>(
            this.tableActiveState.getList(),
            new TableCardFactory(),
            new CardWithMarksComponentFactory(viewLoader),
            viewLoader
        );

        this.cardListComponent.onPrimary((Table eventTable) -> {
            this.tableActiveState.activate(eventTable);
        });

        this.searchComponent = new SearchComponent(viewLoader, this.tableActiveState.searchProperty());

        Subscription<Hotkey> subscription = container.getHotkeyService().on("find", (hotkey) -> {
            this.showSearch();
        });
        this.subscriptions.add(subscription);

        subscription = container.getHotkeyService().on("cancel", (hotkey) -> {
            this.searchComponent.clear();
            this.searchBox.getChildren().clear();
            System.out.println("close find");
        });
        this.subscriptions.add(subscription);

        this.loadView("/layout/table/index.fxml");
    }

    protected void showSearch() {
        this.searchBox.getChildren().add(
            (Node) this.searchComponent.getContent()
        );
        this.searchComponent.focus();
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

        if (!this.tableActiveState.searchProperty().get().isEmpty()) {
            this.showSearch();
        }
    }

    @Override
    @FXML public void addTable() {
        Table newTable = this.tableFactory.createWithCreateChange(this.projectService.getOpened().get(), "new_table");
        this.tableActiveState.addAndActivate(newTable);
        
        Column idColumn = this.columnFactory.createWithCreateChange("id", "integer", "", false, "11", false, "");
        this.columnActiveState.add(idColumn);
        this.columnActiveState.add(
            this.columnFactory.createWithCreateChange("created_at", "timestamp", "CURRENT_TIMESTAMP", false, "", false, "")
        );
        this.columnActiveState.add(
            this.columnFactory.createWithCreateChange("modified_at", "timestamp", "CURRENT_TIMESTAMP", false, "", false, "")
        );
        this.indexActiveState.add(
            this.indexFactory.createWithCreateChange("primary", Arrays.asList(idColumn.nameProperty()))
        );
    }

    @Override
    @FXML public void commit() {
        this.activeRoute.changeTo("commit.view", this.projectService.getOpened().get());
    }
}