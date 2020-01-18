package migrator.app.domain.project.service;

import java.util.Collection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.DatabaseContainer;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.model.Project;

public class SimpleProjectStore implements ProjectStore {
    protected DatabaseContainer databaseContainer;
    protected ObjectProperty<Project> selected;
    protected ObjectProperty<ProjectContainer> opened;
    protected ObservableList<Project> list;

    public SimpleProjectStore(DatabaseContainer databaseContainer) {
        this.databaseContainer = databaseContainer;
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
        this.opened = new SimpleObjectProperty<>();
    }

    @Override
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
    }

    public ObjectProperty<Project> getSelected() {
        return this.selected;
    }

    public void open(Project project) {
        if (project == null) {
            this.opened.set(null);
            return;
        }

        Connection connection = project.getDatabase().getConnection();
        DatabaseStructureFactory dbStrucutreFactory = this.databaseContainer.getStructureFactoryFor(connection.getDriver());
        DatabaseStructure dbStrucutre = dbStrucutreFactory.create(
            project.getDatabase().getUrl(),
            connection.getUser(),
            connection.getPassword()
        );
        
        this.opened.set(new ProjectContainer(project, dbStrucutre));
    }

    public void close() {
        this.open(null);
    }

    public ObjectProperty<ProjectContainer> getOpened() {
        return this.opened;
    }

    public ObservableList<Project> getList() {
        return this.list;
    }

    public void remove(Project project) {
        if (project == this.selected.get()) {
            this.deselect();
        }
        this.list.remove(project);
    }

    public void add(Project project) {
        this.list.add(project);
    }

    @Override
    public void addAll(Collection<Project> projects) {
        if (projects == null) {
            return;
        }
        this.list.addAll(projects);
    }
}