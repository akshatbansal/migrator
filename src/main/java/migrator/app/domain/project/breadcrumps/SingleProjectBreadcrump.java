package migrator.app.domain.project.breadcrumps;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.service.TableActiveState;

public class SingleProjectBreadcrump implements Breadcrump {
    protected ObjectProperty<Project> project;
    protected ProjectService projectService;
    protected StringProperty projectName;
    protected TableActiveState tableActiveState;

    public SingleProjectBreadcrump(ProjectService projectService, ObjectProperty<Project> linkToProject, TableActiveState tableActiveState) {
        this.project = linkToProject;
        this.projectService = projectService;
        this.tableActiveState = tableActiveState;
        this.projectName = new SimpleStringProperty("");

        this.project.addListener((observable, oldValue, newValue) -> {
            this.onProjectChange(newValue);
        });
        this.onProjectChange(this.project.get());
    }

    private void onProjectChange(Project project) {
        if (project == null) {
            this.projectName.set("");
            return;
        }
        this.projectName.set(project.nameProperty().get());
    }

    @Override
    public String getName() {
        return this.nameProperty().get();
    }

    @Override
    public StringProperty nameProperty() {
        return this.projectName;
    }

    @Override
    public void link() {
        this.tableActiveState.deactivate();
        this.projectService.open(this.project.get());
    }
}