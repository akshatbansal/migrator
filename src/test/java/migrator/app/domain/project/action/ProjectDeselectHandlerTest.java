package migrator.app.domain.project.action;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class ProjectDeselectHandlerTest {
    private ProjectService projectService;
    private EventDispatcher dispatcher;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.container = new Container();
        this.projectService = new ProjectService(container);
        this.dispatcher = container.dispatcher();
    }

    @Test
    public void onProjectDeselect_whenNoProjectSelected_projectStoreSelectedIsNull() {
        this.projectService.start();
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.deselect")
        );

        assertNull(this.container.projectStore().getSelected().get());
    }

    @Test
    public void onProjectDeselect_whenOtherProjectSelected_projectStoreSelectedIsNull() {
        this.projectService.start();
        this.container.projectStore().getSelected().set(
            new Project(null, null, null, null, null)
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.deselect")
        );

        assertNull(this.container.projectStore().getSelected().get());
    }
}