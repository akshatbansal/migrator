package migrator.app.project;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.project.property.ProjectProperty;
import migrator.app.project.property.ProjectPropertyJsonAdapter;
import migrator.lib.adapter.SimpleJsonListAdapter;
import migrator.lib.storage.Storage;
import migrator.lib.storage.Storages;

public class ProjectService {
    private Storage<Collection<ProjectProperty>> stoarge;
    private ObservableList<ProjectProperty> projects;

    public ProjectService() {
        Path storageFoldePath = Paths.get(System.getProperty("user.home"), ".migrator");

        this.projects = FXCollections.observableArrayList();
        
        this.stoarge = Storages.getFileStorage(
            new File(storageFoldePath.toString(), "project.json"),
            new SimpleJsonListAdapter<>(
                new ProjectPropertyJsonAdapter()
            )
        );
    }

    public ObservableList<ProjectProperty> getProjects() {
        return this.projects;
    }

    public void start() {
        this.initProjects();
    }

    public void stop() {
        this.storeProjects();
    }

    private void initProjects() {
        this.projects.addAll(
            this.stoarge.load()
        );
    }

    private void storeProjects() {
        this.stoarge.store(
            this.projects
        );
    }
}