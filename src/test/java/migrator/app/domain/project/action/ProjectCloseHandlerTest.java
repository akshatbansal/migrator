package migrator.app.domain.project.action;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.service.ProjectService;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class ProjectCloseHandlerTest {
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
    public void onProjectClose_whenNoProjectOpened_projectStoreOpenedIsNull() {
        this.projectService.start();
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.close")
        );

        assertNull(this.container.projectStore().getOpened().get());
    }

    @Test
    public void onProjectClose_whenOtherProjectOpened_projectStoreOpenedIsNull() {
        this.projectService.start();
        this.container.projectStore().getOpened().set(
            new ProjectContainer(null, null)
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.close")
        );

        assertNull(this.container.projectStore().getOpened().get());
    }
}