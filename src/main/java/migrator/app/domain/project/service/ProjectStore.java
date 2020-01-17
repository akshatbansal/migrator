package migrator.app.domain.project.service;

import java.util.Collection;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.model.Project;

public interface ProjectStore {
    public ObservableList<Project> getList();
    public ObjectProperty<Project> getSelected();
    public ObjectProperty<ProjectContainer> getOpened();
    public void open(Project project);
    public void close();
    public void select(Project project);
    public void deselect();
    public void add(Project project);
    public void addAll(Collection<Project> projects);
    public void remove(Project project);
}