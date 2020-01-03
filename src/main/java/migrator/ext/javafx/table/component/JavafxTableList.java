package migrator.ext.javafx.table.component;

import java.util.Arrays;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.app.breadcrumps.VoidBreadcrump;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.breadcrumps.ProjectsBreadcrump;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.domain.table.service.TableActivator;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.card.CardListComponent;
import migrator.ext.javafx.breadcrumps.JavafxBreadcrumpsComponent;
import migrator.ext.javafx.component.SearchComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxTableList extends ViewComponent implements TableList, TableActivator {
    protected ObjectProperty<ProjectContainer> projectContainer;
    protected StringProperty breadcrumpProjectName;

    protected TableFactory tableFactory;
    protected TableActiveState tableActiveState;
    protected TableGuiKit guiKit;
    protected BreadcrumpsComponent breadcrumpsComponent;
    protected ActiveRoute activeRoute;
    protected CardListComponent<Table> cardListComponent;
    protected SearchComponent searchComponent;
    protected ColumnActiveState columnActiveState;
    protected ColumnFactory columnFactory;

    @FXML protected FlowPane tables;
    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox tableCards;
    @FXML protected VBox searchBox;

    public JavafxTableList(Container container, ObjectProperty<ProjectContainer> projectContainer) {
        super(new ViewLoader());
        this.projectContainer = projectContainer;
        this.breadcrumpProjectName = new SimpleStringProperty("");

        this.activeRoute = container.getActiveRoute();
        this.tableFactory = container.getTableFactory();
        this.tableActiveState = container.getTableActiveState();
        this.columnActiveState = container.getColumnActiveState();
        this.columnFactory = container.getColumnFactory();

        this.breadcrumpsComponent = new JavafxBreadcrumpsComponent(Arrays.asList(
            new ProjectsBreadcrump(
                container.getProjectService()
            ),
            new VoidBreadcrump(this.breadcrumpProjectName)
        ));

        this.cardListComponent = new CardListComponent<>(
            this.tableActiveState.getList(),
            new TableCardFactory(viewLoader, this),
            viewLoader
        );

        this.searchComponent = new SearchComponent(viewLoader, this.tableActiveState.searchProperty());

        container.getHotkeyService().on("find", (hotkey) -> {
            this.showSearch();
        });
        container.getHotkeyService().on("cancel", (hotkey) -> {
            this.searchComponent.close();
        });

        this.searchComponent.onClose((Object _any) -> {
            this.searchBox.getChildren().clear();
        });

        this.loadView("/layout/table/index.fxml");

        this.projectContainer.addListener((observable, oldValue, newValue) -> {
            this.onProjectChange(newValue);
        });
        this.onProjectChange(this.projectContainer.get());
    }

    private void onProjectChange(ProjectContainer projectContainer) {
        if (projectContainer == null) {
            this.breadcrumpProjectName.set("");
            return;
        }
        this.breadcrumpProjectName.set(projectContainer.getProject().nameProperty().get());
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
        Table newTable = this.tableFactory.createWithCreateChange(this.projectContainer.get().getProject().getId(), "new_table");
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
        this.activeRoute.changeTo("commit.view");
    }

    @Override
    public void activateTable(Table table) {
        this.tableActiveState.activate(table);
    }
}