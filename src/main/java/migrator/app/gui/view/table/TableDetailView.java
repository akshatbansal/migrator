package migrator.app.gui.view.table;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.component.ComponentFactories;
import migrator.app.gui.component.breadcrump.Breadcrump;
import migrator.app.gui.component.breadcrump.BreadcrumpsComponent;
import migrator.app.gui.component.breadcrump.RouteBreadcrump;
import migrator.app.gui.component.breadcrump.VoidBreadcrump;
import migrator.app.gui.component.column.ColumnListComponent;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.gui.view.ViewFactories;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class TableDetailView extends SimpleView implements View {
    protected StringProperty breadcrumpTableName;
    protected StringProperty breadcrumpProjectName;
    protected ObjectProperty<Table> activeTable;
    protected ObjectProperty<ProjectContainer> activeProject;

    protected BreadcrumpsComponent breadcrumpsComponent;
    protected ColumnListComponent columnListComponent;
    protected IndexList indexList;

    private EventDispatcher dispatcher;

    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;
    @FXML protected Text title;

    public TableDetailView(
        EventDispatcher dispatcher,
        ViewFactories viewFactories,
        ComponentFactories componentFactories,
        ObservableList<Column> columns
    ) {
        super();
        this.dispatcher = dispatcher;
        this.breadcrumpProjectName = new SimpleStringProperty("");
        this.breadcrumpTableName = new SimpleStringProperty("");

        ObservableList<Breadcrump> breadcrumps = FXCollections.observableArrayList();
        breadcrumps.add(
            new RouteBreadcrump("Projects", dispatcher, "projects")
        );
        breadcrumps.add(
            new RouteBreadcrump(this.breadcrumpProjectName, dispatcher, "project")
        );
        breadcrumps.add(
            new VoidBreadcrump(this.breadcrumpTableName)
        );
        this.breadcrumpsComponent = componentFactories.createBreadcrumps();
        this.breadcrumpsComponent.bind(breadcrumps);

        this.columnListComponent = componentFactories.createColumnList();
        this.columnListComponent.bind(columns);
        this.columnListComponent.outputs().addListener((observable, oldValue, newValue) -> {
            if (newValue.getName().equals("select")) {
                this.onColumnSelect((Column) newValue.getValue());
            } else if (newValue.getName().equals("add")) {
                this.createColumn();
            }
        });

        this.loadFxml("/layout/table/view.fxml");

        this.breadcrumpsContainer.getChildren().setAll(
            this.breadcrumpsComponent.getNode()
        );

        // this.indexList = this.tableGuiKit.createIndexList();
        // this.indexList.onSelect((Index selectedIndex) -> {
        //     this.columnList.deselect();
        // });

        this.body.getChildren()
            .setAll(
                this.columnListComponent.getNode()
            );
    }

    public void bind(ObjectProperty<Table> table) {
        this.activeTable = table;
        this.activeTable.addListener((observable, oldValue, newValue) -> {
            this.onTableChange(newValue);
        });
        this.onTableChange(this.activeTable.get());
    }

    public void bindProject(ObjectProperty<ProjectContainer> projectContainer) {
        this.activeProject = projectContainer;
        this.activeProject.addListener((observable, oldValue, newValue) -> {
            this.onProjectChange(newValue);
        });
        this.onProjectChange(this.activeProject.get());
    }

    private void onProjectChange(ProjectContainer project) {
        if (project == null) {
            this.breadcrumpProjectName.set("");
            return;
        }
        this.breadcrumpProjectName.set(project.getProject().nameProperty().get());
    }

    private void onTableChange(Table table) {
        this.title.textProperty().unbind();
        if (table == null) {
            this.breadcrumpTableName.unbind();
            return;
        }
        this.breadcrumpTableName.bind(table.nameProperty());
        this.title.textProperty().bind(table.nameProperty());
    }

    private void onColumnSelect(Column column) {
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.select", column)
        );
    }

    private void createColumn() {
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.create")
        );
    }
}