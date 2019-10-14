package migrator.app.domain.project.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.project.model.Project;
import migrator.app.router.ActiveRoute;
import migrator.app.toast.ToastService;
import migrator.lib.persistance.ListPersistance;
import migrator.lib.persistance.Persistance;

public class ProjectService {
    protected DatabaseDriverManager databaseDriverManager;
    protected ProjectFactory factory;
    protected ToastService toastService;
    protected ActiveRoute activeRoute;
    protected ObjectProperty<Project> selected;
    protected ObjectProperty<Project> opened;
    protected ObservableList<Project> list;

    public ProjectService(ProjectFactory factory, DatabaseDriverManager databaseDriverManager, ToastService toastService, ActiveRoute activeRoute) {
        this.factory = factory;
        this.databaseDriverManager = databaseDriverManager;
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
        this.selected.set(project);
        if (project != null) {
            this.activeRoute.changeTo("project.view", project);
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
        if (project != null) {
            DatabaseDriver databaseDriver  = this.databaseDriverManager
                .createDriver(project.getDatabase());
            databaseDriver.connect();
            if (!databaseDriver.isConnected()) {
                this.toastService.error(databaseDriver.getError());
                return;
            }
        }
        this.opened.set(project);
        if (project != null) {
            this.activeRoute.changeTo("table.index");
        }
    }

    public void close() {
        this.open(null);
        this.activeRoute.changeTo("project.index");
    }

    public ObjectProperty<Project> getOpened() {
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