package migrator.app.domain.project.breadcrumps;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.domain.project.service.ProjectService;

public class ProjectsBreadcrump implements Breadcrump {
    protected StringProperty name;
    protected ProjectService projectService;

    public ProjectsBreadcrump(ProjectService projectService) {
        this.projectService = projectService;
        this.name = new SimpleStringProperty("Projects");
    }

    @Override
    public String getName() {
        return this.nameProperty().get();
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }

    @Override
    public void link() {
        this.projectService.close();
    }
}