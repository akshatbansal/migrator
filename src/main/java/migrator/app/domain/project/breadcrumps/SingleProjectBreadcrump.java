package migrator.app.domain.project.breadcrumps;

import javafx.beans.property.StringProperty;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;

public class SingleProjectBreadcrump implements Breadcrump {
    protected Project project;
    protected ProjectService projectService;

    public SingleProjectBreadcrump(ProjectService projectService, Project linkToProject) {
        this.project = linkToProject;
        this.projectService = projectService;
    }

    @Override
    public String getName() {
        return this.nameProperty().get();
    }

    @Override
    public StringProperty nameProperty() {
        return this.project.nameProperty();
    }

    @Override
    public void link() {
        this.projectService.open(this.project);
    }
}