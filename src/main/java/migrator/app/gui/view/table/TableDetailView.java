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
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.component.ComponentFactories;
import migrator.app.gui.component.breadcrump.Breadcrump;
import migrator.app.gui.component.breadcrump.BreadcrumpsComponent;
import migrator.app.gui.component.breadcrump.EventBreadcrump;
import migrator.app.gui.component.breadcrump.VoidBreadcrump;
import migrator.app.gui.component.column.ColumnListComponent;
import migrator.app.gui.component.index.IndexListComponent;
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
    protected IndexListComponent indexListComponent;

    private EventDispatcher dispatcher;

    @FXML protected VBox breadcrumpsContainer;
    @FXML protected VBox body;
    @FXML protected Text title;

    public TableDetailView(
        EventDispatcher dispatcher,
        ViewFactories viewFactories,
        ComponentFactories componentFactories,
        ObservableList<Column> columns,
        ObjectProperty<Column> selectedColumns,
        ObservableList<Index> indexes,
        ObjectProperty<Index> selectedIndex
    ) {
        super();
        this.dispatcher = dispatcher;
        this.breadcrumpProjectName = new SimpleStringProperty("");
        this.breadcrumpTableName = new SimpleStringProperty("");

        ObservableList<Breadcrump> breadcrumps = FXCollections.observableArrayList();
        breadcrumps.add(
            new EventBreadcrump("Projects", dispatcher, "project.close")
        );
        breadcrumps.add(
            new EventBreadcrump(this.breadcrumpProjectName, dispatcher, "table.deselect")
        );
        breadcrumps.add(
            new VoidBreadcrump(this.breadcrumpTableName)
        );
        this.breadcrumpsComponent = componentFactories.createBreadcrumps();
        this.breadcrumpsComponent.bind(breadcrumps);

        this.columnListComponent = this.createColumnList(componentFactories, columns);
        this.indexListComponent = this.createIndexList(componentFactories, indexes);

        selectedColumns.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                this.columnListComponent.deselect();
            }
        });
        selectedIndex.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                this.indexListComponent.deselect();
            }
        });

        this.loadFxml("/layout/table/view.fxml");

        this.breadcrumpsContainer.getChildren().setAll(
            this.breadcrumpsComponent.getNode()
        );

        this.body.getChildren()
            .setAll(
                this.columnListComponent.getNode(),
                this.indexListComponent.getNode()
            );
    }

    private ColumnListComponent createColumnList(ComponentFactories componentFactories, ObservableList<Column> columns) {
        ColumnListComponent component = componentFactories.createColumnList();
        component.bind(columns);
        component.outputs().addListener((observable, oldValue, newValue) -> {
            if (newValue.getName().equals("select")) {
                this.onColumnSelect((Column) newValue.getValue());
            } else if (newValue.getName().equals("add")) {
                this.createColumn();
            }
        });
        return component;
    }

    private IndexListComponent createIndexList(ComponentFactories componentFactories, ObservableList<Index> indexes) {
        IndexListComponent component = componentFactories.createIndexList();
        component.bind(indexes);
        component.outputs().addListener((observable, oldValue, newValue) -> {
            if (newValue.getName().equals("select")) {
                this.onIndexSelect((Index) newValue.getValue());
            } else if (newValue.getName().equals("create")) {
                this.createIndex();
            }
        });
        return component;
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
        this.indexListComponent.deselect();
        this.dispatcher.dispatch(
            new SimpleEvent<>("index.deselect")
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.select", column)
        );
    }

    private void createColumn() {
        Table table = this.activeTable.get();
        if (table == null) {
            return;
        }
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.create", table.getUniqueKey())
        );
    }

    private void onIndexSelect(Index index) {
        this.columnListComponent.deselect();
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.deselect")
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("index.select", index)
        );
    }

    private void createIndex() {
        Table table = this.activeTable.get();
        if (table == null) {
            return;
        }
        this.dispatcher.dispatch(
            new SimpleEvent<>("index.create", table.getUniqueKey())
        );
    }
}