package migrator.app.domain.project.action;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ProjectRemoveHandler implements EventHandler {
    private ProjectStore projectStore;

    public ProjectRemoveHandler(ProjectStore projectStore) {
        this.projectStore = projectStore;
    }

    @Override
    public void handle(Event<?> event) {
        this.projectStore.remove((Project) event.getValue());
    }
}