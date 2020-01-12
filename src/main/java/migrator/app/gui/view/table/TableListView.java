package migrator.app.gui.view.table;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.component.breadcrump.Breadcrump;
import migrator.app.gui.component.breadcrump.BreadcrumpsComponent;
import migrator.app.gui.component.breadcrump.RouteBreadcrump;
import migrator.app.gui.component.breadcrump.VoidBreadcrump;
import migrator.app.gui.component.card.CardListComponent;
import migrator.app.gui.component.table.TableCardComponentFactory;
import migrator.app.gui.route.RouteChangeEvent;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class TableListView extends SimpleView implements View {
    protected EventDispatcher dispatcher;
    protected CardListComponent<Table> cardListComponent;
    protected StringProperty projectBreadcrumpName;

    @FXML protected VBox tableCards;
    @FXML protected VBox breadcrumpsContainer;

    public TableListView(EventDispatcher dispatcher, ObservableList<Table> tables, ObjectProperty<ProjectContainer> projectContainer) {
        super();
        this.dispatcher = dispatcher;
        this.projectBreadcrumpName = new SimpleStringProperty("");

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
        this.cardListComponent.bind(tables);
        this.cardListComponent.outputs().addListener((observable, oldValue, newValue) -> {
            if (newValue.getName().equals("edit")) {
                this.dispatcher.dispatch(
                    new SimpleEvent<>("table.select", (Table) newValue.getValue())
                );
            }
        });

        BreadcrumpsComponent breadcrumpsComponent = this.createBreadcumpsComponent();

        this.loadFxml("/layout/table/index.fxml");

        this.breadcrumpsContainer.getChildren()
            .setAll(breadcrumpsComponent.getNode());
        this.tableCards.getChildren()
            .setAll(this.cardListComponent.getNode());
    }

    private BreadcrumpsComponent createBreadcumpsComponent() {
        BreadcrumpsComponent breadcrumpsComponent = new BreadcrumpsComponent();

        ObservableList<Breadcrump> breadcrumps = FXCollections.observableArrayList();
        breadcrumps.add(
            new RouteBreadcrump("Projects", this.dispatcher, "projects")
        );
        breadcrumps.add(
            new VoidBreadcrump(this.projectBreadcrumpName)
        );

        breadcrumpsComponent.bind(breadcrumps);

        return breadcrumpsComponent;
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