package migrator.app.domain.project.breadcrumps;

import javafx.beans.property.StringProperty;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.service.TableActiveState;

public class SingleProjectBreadcrump implements Breadcrump {
    protected Project project;
    protected ProjectService projectService;
    protected TableActiveState tableActiveState;

    public SingleProjectBreadcrump(ProjectService projectService, Project linkToProject, TableActiveState tableActiveState) {
        this.project = linkToProject;
        this.projectService = projectService;
        this.tableActiveState = tableActiveState;
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
        this.tableActiveState.deactivate();
        this.projectService.open(this.project);
    }
}