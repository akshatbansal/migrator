package migrator.ext.javafx.table.component;

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
import migrator.app.domain.table.service.TableActivator;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.card.CardListComponent;
import migrator.ext.javafx.component.SearchComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Subscription;

public class JavafxTableList extends ViewComponent implements TableList, TableActivator {
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
            this.projectService.getOpened().get().getProject()
        );

        this.cardListComponent = new CardListComponent<>(
            this.tableActiveState.getList(),
            new TableCardFactory(viewLoader, this),
            viewLoader
        );

        this.searchComponent = new SearchComponent(viewLoader, this.tableActiveState.searchProperty());

        Subscription<?> subscription = container.getHotkeyService().on("find", (hotkey) -> {
            this.showSearch();
        });
        this.subscriptions.add(subscription);

        subscription = container.getHotkeyService().on("cancel", (hotkey) -> {
            this.searchComponent.close();
        });
        this.subscriptions.add(subscription);

        subscription = this.searchComponent.onClose((Object _any) -> {
            this.searchBox.getChildren().clear();
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
        Table newTable = this.tableFactory.createWithCreateChange(this.projectService.getOpened().get().getProject().getId(), "new_table");
        this.tableActiveState.addAndActivate(newTable);
        
        Column idColumn = this.columnFactory.createWithCreateChange(newTable.getUniqueKey(), "id", "integer", null, false, "11", false, "", true);
        this.columnActiveState.add(idColumn);
        this.columnActiveState.add(
            this.columnFactory.createWithCreateChange(newTable.getUniqueKey(), "created_at", "timestamp", "CURRENT_TIMESTAMP", false, "", false, "", false)
        );
        this.columnActiveState.add(
            this.columnFactory.createWithCreateChange(newTable.getUniqueKey(), "modified_at", "timestamp", "CURRENT_TIMESTAMP", false, "", false, "", false)
        );
    }

    @Override
    @FXML public void commit() {
        this.activeRoute.changeTo("commit.view", this.projectService.getOpened().get());
    }

    @Override
    public void activateTable(Table table) {
        this.tableActiveState.activate(table);
    }
}