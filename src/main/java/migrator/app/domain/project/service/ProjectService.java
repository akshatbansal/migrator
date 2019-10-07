package migrator.app.domain.project.service;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.domain.project.model.Project;

public class ProjectService {
    protected ProjectFactory factory;
    protected ObjectProperty<Project> selected;
    protected ObjectProperty<Project> opened;
    protected ObservableList<Project> list;

    public ProjectService(ProjectFactory factory) {
        this.factory = factory;
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