package migrator.app.domain.project.action;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ProjectOpenHandler implements EventHandler {
    protected ProjectStore projectStore;

    public ProjectOpenHandler(ProjectStore projectStore) {
        this.projectStore = projectStore;
    }

    @Override
    public void handle(Event<?> event) {
        Project project = (Project) event.getValue();
        this.projectStore.open(project);
    }
}