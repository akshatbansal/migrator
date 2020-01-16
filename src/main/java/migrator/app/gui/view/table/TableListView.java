package migrator.app.gui.view.table;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.component.ComponentFactories;
import migrator.app.gui.component.breadcrump.Breadcrump;
import migrator.app.gui.component.breadcrump.BreadcrumpsComponent;
import migrator.app.gui.component.breadcrump.EventBreadcrump;
import migrator.app.gui.component.breadcrump.VoidBreadcrump;
import migrator.app.gui.component.card.CardListComponent;
import migrator.app.gui.component.list.SearchComponent;
import migrator.app.gui.component.table.TableCardComponentFactory;
import migrator.app.gui.route.RouteChangeEvent;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;
import migrator.lib.hotkyes.HotkeysService;

public class TableListView extends SimpleView implements View {
    protected EventDispatcher dispatcher;
    protected CardListComponent<Table> cardListComponent;
    protected StringProperty projectBreadcrumpName;
    private BooleanProperty searchVisible;
    private StringProperty searchValue;
    private ComponentFactories componentFactories;
    private ObservableList<Table> filteredList;
    private ObservableList<Table> tables;

    @FXML protected VBox tableCards;
    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox searchBox;

    public TableListView(
        EventDispatcher dispatcher,
        ComponentFactories componentFactories,
        HotkeysService hotkeysService,
        ObservableList<Table> tables,
        ObjectProperty<ProjectContainer> projectContainer
    ) {
        super();
        this.dispatcher = dispatcher;
        this.componentFactories = componentFactories;
        this.projectBreadcrumpName = new SimpleStringProperty("");
        this.searchVisible = new SimpleBooleanProperty(false);
        this.searchValue = new SimpleStringProperty("");
        this.filteredList = FXCollections.observableArrayList();
        this.tables = tables;

        this.tables.addListener((Change<? extends Table> change) -> {
            this.filter();
        });

        projectContainer.addListener((observable, oldvalue, newValue) -> {
            if (newValue == null) {
                this.projectBreadcrumpName.unbind();
                this.projectBreadcrumpName.set("");
                return;
            }
            this.projectBreadcrumpName.bind(newValue.getProject().nameProperty());
        });

        this.cardListComponent = new CardListComponent<>(
            new TableCardComponentFactory()
        );
        this.cardListComponent.bind(this.filteredList);
        this.cardListComponent.outputs().addListener((observable, oldValue, newValue) -> {
            if (newValue.getName().equals("edit")) {
                this.dispatcher.dispatch(
                    new SimpleEvent<>("table.select", (Table) newValue.getValue())
                );
            }
        });

        BreadcrumpsComponent breadcrumpsComponent = this.createBreadcumpsComponent();

        SearchComponent searchComponent = this.createSearchComponent(this.searchValue);

        this.loadFxml("/layout/table/index.fxml");

        this.breadcrumpsContainer.getChildren()
            .setAll(breadcrumpsComponent.getNode());
        this.tableCards.getChildren()
            .setAll(this.cardListComponent.getNode());
        this.searchBox.getChildren()
            .setAll(searchComponent.getNode());
        this.searchBox.visibleProperty().bind(this.searchVisible);

        hotkeysService.on("find", (hotkey) -> {
            this.searchVisible.set(true);
            searchComponent.focus();
        });
        hotkeysService.on("cancel", (hotkey) -> {
            this.closeSearch();
        });
    }

    private void filter() {
        if (this.searchValue.get().isEmpty()) {
            this.filteredList.setAll(this.tables);
            return;
        }
        List<Table> newFiltered = new LinkedList<>();
        for (Table item : this.tables) {
            if (!item.getName().contains(this.searchValue.get())) {
                continue;
            }
            newFiltered.add(item);
        }
        this.filteredList.setAll(newFiltered);
    }

    private void closeSearch() {
        this.searchVisible.set(false);
        this.searchValue.set("");
    }

    private BreadcrumpsComponent createBreadcumpsComponent() {
        BreadcrumpsComponent breadcrumpsComponent = new BreadcrumpsComponent();

        ObservableList<Breadcrump> breadcrumps = FXCollections.observableArrayList();
        breadcrumps.add(
            new EventBreadcrump("Projects", this.dispatcher, "project.close")
        );
        breadcrumps.add(
            new VoidBreadcrump(this.projectBreadcrumpName)
        );

        breadcrumpsComponent.bind(breadcrumps);

        return breadcrumpsComponent;
    }

    private SearchComponent createSearchComponent(StringProperty searchValue) {
        SearchComponent component = this.componentFactories.createSearch();
        component.bind(searchValue);
        component.outputs().addListener((observable, oldValue, newValue) -> {
            if (newValue.getName().equals("close")) {
                this.closeSearch();
            }
        });

        searchValue.addListener((observable, oldValue, newValue) -> {
            this.filter();
        });

        return component;
    }

    @FXML public void addTable() {
        this.dispatcher.dispatch(
            new SimpleEvent<Table>("table.new")
        );    
    }

    @FXML public void commit() {
        this.dispatcher.dispatch(
            new RouteChangeEvent("commit")
        );
    }

    public void activateTable(Table table) {
        this.dispatcher.dispatch(
            new SimpleEvent<Table>("table.select", table)
        );
    }
}