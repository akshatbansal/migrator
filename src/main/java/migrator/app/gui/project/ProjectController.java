package migrator.app.gui.project;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.app.database.ConnectionResult;
import migrator.app.project.ProjectContainer;
import migrator.app.project.ProjectContainerFactory;
import migrator.app.project.property.ProjectProperty;
import migrator.app.project.property.SimpleDatabaseProperty;
import migrator.app.project.property.SimpleProjectProperty;
import migrator.app.toast.ToastService;
import migrator.lib.adapter.Adapter;

public class ProjectController {
    protected ObservableList<ProjectGuiModel> projects;
    protected ObservableList<ProjectProperty> properties;
    protected ObjectProperty<ProjectGuiModel> selected;
    protected ObjectProperty<ProjectContainer> opened;
    
    protected ToastService toastService;
    protected ProjectContainerFactory projectContainerFactory;
    protected Adapter<ProjectGuiModel, ProjectProperty> adapter;
    
    protected ObservableList<String> databaseDrivers;
    protected ObservableList<String> outputs; 

    public ProjectController(
        ObservableList<ProjectProperty> properties,
        ObservableList<String> databaseDrivers,
        ObservableList<String> outputs,
        ObjectProperty<ProjectContainer> opened,
        ProjectContainerFactory projectContainerFactory,
        ToastService toastService
    ) {
        this.properties = properties;
        this.projects = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
        this.opened = opened;
        this.projectContainerFactory = projectContainerFactory;
        this.toastService = toastService;

        this.adapter = new ProjectGuiAdapter();
        this.databaseDrivers = databaseDrivers;
        this.outputs = outputs;

        properties.addListener((Change<? extends ProjectProperty> change) -> {
            while (change.next()) {
                this.addProperties(change.getAddedSubList());
                this.removeProperties(change.getRemoved());
            }
        });
        this.addProperties(properties);
    }

    public ObjectProperty<ProjectGuiModel> selectedProperty() {
        return this.selected;
    }
    
    public void select(ProjectGuiModel project) {
        if (project == this.selected.get()) {
            return;
        }
        this.deselect();
        this.selected.set(project);
        if (project == null) {
            return;
        }
        project.enable("selected");
    }

    public void deselect() {
        if (this.selected.get() == null) {
            return;
        }
        this.selected.get().disable("selected");
        this.selected.set(null);
    }

    public void add(ProjectProperty project) {
        this.properties.add(project);
    }

    public void create(String projectName) {
        ProjectProperty project = new SimpleProjectProperty(
            "",
            projectName,
            "",
            "phinx",
            new SimpleDatabaseProperty(
                "mysql://localhost:3306/database",
                "user",
                "",
                "mysql"
            )
        );
        this.add(project);
    }

    public void remove(ProjectGuiModel project) {
        this.properties.remove(project.getProjectProperty());
    }

    public ObservableList<ProjectGuiModel> getList() {
        return this.projects;
    }

    private void addProperties(List<? extends ProjectProperty> properties) {
        List<ProjectGuiModel> models = new LinkedList<>();
        for (ProjectProperty property : properties) {
            models.add(
                this.adapter.concretize(property)
            );
        }
        this.projects.addAll(models);
    }

    private void removeProperties(List<? extends ProjectProperty> properties) {
        List<ProjectGuiModel> modelsToRemove = new LinkedList<>();
        for (ProjectProperty property : properties) {
            for (ProjectGuiModel project : this.projects) {
                if (project.getProjectProperty() != property) {
                    continue;
                }
                modelsToRemove.add(project);
            }
        }
        this.projects.removeAll(modelsToRemove);
    }

    public ObservableList<String> getDatabaseDriverList() {
        return this.databaseDrivers;
    }

    public ObservableList<String> getOutputList() {
        return this.outputs;
    }

    public void open(ProjectGuiModel project) {
        if (project == null) {
            this.opened.set(null);
            return;
        }

        if (project.attribute("disabled").get()) {
            return;
        }

        project.enable("disabled");
        
        ProjectContainer projectContainer = this.projectContainerFactory.create(
            project.getProjectProperty()
        );
        ConnectionResult<?> connectionResult = projectContainer.getDatabaseStructure().testConnection();

        project.disable("disabled");
        if (!connectionResult.isOk()) {
            this.toastService.error(connectionResult.getError());
            return;
        }
        
        this.opened.set(projectContainer);
    }
}