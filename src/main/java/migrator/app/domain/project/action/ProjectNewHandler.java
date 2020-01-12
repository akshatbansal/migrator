package migrator.app.domain.project.action;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectFactory;
import migrator.app.domain.project.service.ProjectStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ProjectNewHandler implements EventHandler {
    private ProjectStore projectStore;
    private ProjectFactory projectFactory;

    public ProjectNewHandler(ProjectStore projectStore, ProjectFactory projectFactory) {
        this.projectStore = projectStore;
        this.projectFactory = projectFactory;
    }

    @Override
    public void handle(Event<?> event) {
        Project project = this.projectFactory.create("new_project");
        this.projectStore.add(project);
        this.projectStore.select(project);
    }
}