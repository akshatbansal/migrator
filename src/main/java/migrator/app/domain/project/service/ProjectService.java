package migrator.app.domain.project.service;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.ConnectionResult;
import migrator.app.database.DatabaseContainer;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.model.Project;
import migrator.app.router.ActiveRoute;
import migrator.app.toast.ToastService;

public class ProjectService {
    protected DatabaseContainer databaseContainer;
    protected ProjectFactory factory;
    protected ToastService toastService;
    protected ActiveRoute activeRoute;
    protected ObjectProperty<Project> selected;
    protected ObjectProperty<ProjectContainer> opened;
    protected ObservableList<Project> list;

    public ProjectService(ProjectFactory factory, DatabaseContainer databaseContainer, ToastService toastService, ActiveRoute activeRoute) {
        this.factory = factory;
        this.databaseContainer = databaseContainer;
        this.toastService = toastService;
        this.activeRoute = activeRoute;
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
        this.opened = new SimpleObjectProperty<>();
    }

    public ProjectFactory getFactory() {
        return this.factory;
    }

    public void select(Project project) {
        if (this.selected.get() != null) {
            this.selected.get().blur();
        }

        this.selected.set(project);
        if (project != null) {
            this.selected.get().focus();
        }
    }

    public void deselect() {
        this.select(null);
        this.activeRoute.changeTo("project.index");
    }

    public ObjectProperty<Project> getSelected() {
        return this.selected;
    }

    public void open(Project project) {
        if (project == null) {
            this.opened.set(null);
            return;
        }

        if (project.disabledProperty().get()) {
            return;
        }
        project.disable();

        Connection connection = project.getDatabase().getConnection();
        DatabaseStructureFactory dbStrucutreFactory = this.databaseContainer.getStructureFactoryFor(connection.getDriver());
        DatabaseStructure dbStrucutre = dbStrucutreFactory.create(
            project.getDatabase().getUrl(),
            connection.getUser(),
            connection.getPassword()
        );
        ConnectionResult<?> connectionResult = dbStrucutre.testConnection();
        project.enable();
        if (!connectionResult.isOk()) {
            this.toastService.error(connectionResult.getError());
            return;
        }
        
        this.opened.set(new ProjectContainer(project, dbStrucutre));
        this.activeRoute.changeTo("table.index");
    }

    public void close() {
        this.open(null);
        this.activeRoute.changeTo("project.index");
    }

    public ObjectProperty<ProjectContainer> getOpened() {
        return this.opened;
    }

    public ObservableList<Project> getList() {
        return this.list;
    }

    public void remove(Project project) {
        this.list.remove(project);
    }

    public void add(Project project) {
        this.list.add(project);
    }
}