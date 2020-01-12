package migrator.app.gui.view;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import migrator.app.boot.Container;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.component.ComponentFactories;
import migrator.app.gui.validation.ProjectConnectionValidator;
import migrator.app.gui.view.main.Layout;
import migrator.app.gui.view.project.ProjectFormView;
import migrator.app.gui.view.project.ProjectListView;
import migrator.app.gui.view.project.ProjectView;
import migrator.app.gui.view.project.ProjectsView;
import migrator.app.gui.view.table.TableDetailView;
import migrator.app.gui.view.table.TableFormView;
import migrator.app.gui.view.table.TableListView;
import migrator.app.gui.view.table.TableView;

public class ViewFactories {
    private Container container;
    private Window window;
    private ComponentFactories componentFactories;

    public ViewFactories(Container container, ComponentFactories componentFactories, Window window) {
        this.window = window;
        this.container = container;
        this.componentFactories = componentFactories;
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
            container.indexContainer().indexStore().getList()
        );
    }

    public TableView createTable() {
        return new TableView(
            new Layout(),
            this,
            this.container.tableContainer().tableStore().getSelected(),
            this.container.projectStore().getOpened()
        );
    }

    public TableFormView createTableForm() {
        return new TableFormView(
            container.dispatcher()
        );
    }
}