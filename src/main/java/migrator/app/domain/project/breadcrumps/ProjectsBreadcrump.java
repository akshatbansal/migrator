package migrator.app.domain.project.breadcrumps;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.service.TableActiveState;

public class ProjectsBreadcrump implements Breadcrump {
    protected StringProperty name;
    protected ProjectService projectService;
    protected TableActiveState tableActiveState;

    public ProjectsBreadcrump(ProjectService projectService, TableActiveState tableActiveState) {
        this.projectService = projectService;
        this.tableActiveState = tableActiveState;
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