package migrator.app.gui.view;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import migrator.app.boot.Container;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.app.gui.component.ComponentFactories;
import migrator.app.gui.validation.ProjectConnectionValidator;
import migrator.app.gui.view.column.ColumnFormView;
import migrator.app.gui.view.commit.CommitFormView;
import migrator.app.gui.view.commit.CommitOverviewView;
import migrator.app.gui.view.commit.CommitView;
import migrator.app.gui.view.index.IndexFormView;
import migrator.app.gui.view.main.Layout;
import migrator.app.gui.view.project.ProjectFormView;
import migrator.app.gui.view.project.ProjectListView;
import migrator.app.gui.view.project.ProjectView;
import migrator.app.gui.view.project.ProjectsView;
import migrator.app.gui.view.table.TableDetailView;
import migrator.app.gui.view.table.TableFormView;
import migrator.app.gui.view.table.TableListView;
import migrator.app.gui.view.table.TableView;
import migrator.app.migration.model.ColumnProperty;

public class ViewFactories {
    private Container container;
    private Window window;
    private ComponentFactories componentFactories;
    private ColumnFormatCollection columnFormatCollection;
    private ObservableList<ColumnProperty> activeColumns;

    public ViewFactories(
        Container container,
        ComponentFactories componentFactories,
        Window window,
        ColumnFormatCollection columnFormatCollection,
        ObservableList<ColumnProperty> activeColumns
    ) {
        this.window = window;
        this.container = container;
        this.componentFactories = componentFactories;
        this.columnFormatCollection = columnFormatCollection;
        this.activeColumns = activeColumns;
    }

    public ProjectFormView createProjectForm(ObjectProperty<Project> selectedProject, ObservableList<String> dbDrivers, ObservableList<String> outputDrivers) {
        return new ProjectFormView(
            this.container.dispatcher(),
            selectedProject,
            dbDrivers,
            outputDrivers,
            this.window,
            new ProjectConnectionValidator(
                this.container.databaseContainer()
            )
        );
    }

    public ProjectListView createProjectList(ObservableList<Project> projects) {
        return new ProjectListView(
            this.container.dispatcher(),
            projects,
            new ProjectConnectionValidator(
                this.container.databaseContainer()
            )
        );
    }

    public TableListView createTableList(ObservableList<Table> tables) {
        return new TableListView(
            this.container.dispatcher(),
            tables,
            this.container.projectStore().getOpened()
        );
    }

    public ProjectsView createProjects() {
        return new ProjectsView(
            new Layout(),
            this,
            container.projectStore().getList(),
            container.projectStore().getSelected(),
            container.databaseContainer().getDrivers(),
            container.migrationContainer().getGeneratorNames()
        );
    }

    public ProjectView createProject() {
        return new ProjectView(
            new Layout(),
            this,
            container.tableContainer().tableStore().getList()
        );
    }

    public TableDetailView createTableDetail() {
        return new TableDetailView(
            container.dispatcher(),
            this,
            this.componentFactories,
            container.columnContainer().columnStore().getList(),
            container.columnContainer().columnStore().getSelected(),
            container.indexContainer().indexStore().getList(),
            container.indexContainer().indexStore().getSelected()
        );
    }

    public TableView createTable() {
        return new TableView(
            new Layout(),
            this,
            this.container.tableContainer().tableStore().getSelected(),
            this.container.projectStore().getOpened(),
            this.container.columnContainer().columnStore().getSelected(),
            this.container.indexContainer().indexStore().getSelected(),
            this.columnFormatCollection.getObservable(),
            this.activeColumns
        );
    }

    public TableFormView createTableForm() {
        return new TableFormView(
            container.dispatcher()
        );
    }

    public ColumnFormView createColumnForm() {
        return new ColumnFormView(
            this.container.dispatcher(),
            this.columnFormatCollection
        );
    }

    public IndexFormView createIndexForm() {
        return new IndexFormView(
            this.container.dispatcher()
        );
    }

    public CommitFormView createCommitForm() {
        return new CommitFormView(
            this.container.dispatcher()
        );
    }

    public CommitOverviewView createCommitOverview() {
        return new CommitOverviewView(
            this.componentFactories,
            this.container.dispatcher()
        );
    }

    public CommitView createCommit() {
        return new CommitView(
            new Layout(),
            this,
            this.container.projectStore().getOpened(),
            this.container.tableContainer().tableStore().getList()
        );
    }
}