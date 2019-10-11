package migrator.app.domain.project.service;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.project.model.Project;
import migrator.app.toast.ToastService;

public class ProjectService {
    protected DatabaseDriverManager databaseDriverManager;
    protected ProjectFactory factory;
    protected ToastService toastService;
    protected ObjectProperty<Project> selected;
    protected ObjectProperty<Project> opened;
    protected ObservableList<Project> list;

    public ProjectService(ProjectFactory factory, DatabaseDriverManager databaseDriverManager, ToastService toastService) {
        this.factory = factory;
        this.databaseDriverManager = databaseDriverManager;
        this.toastService = toastService;
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
        this.opened = new SimpleObjectProperty<>();
    }

    public ProjectFactory getFactory() {
        return this.factory;
    }

    public void select(Project project) {
        this.selected.set(project);
    }

    public void deselect() {
        this.select(null);
    }

    public ObjectProperty<Project> getSelected() {
        return this.selected;
    }

    public void open(Project project) {
        if (project != null) {
            String repositryKey = project.getName();

            DatabaseDriver databaseDriver  = this.databaseDriverManager
                .createDriver(project.getDatabase());
            databaseDriver.connect();
            if (!databaseDriver.isConnected()) {
                this.toastService.show(databaseDriver.getError());
                return;
            }
        }
        this.opened.set(project);
    }

    public void close() {
        this.open(null);
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