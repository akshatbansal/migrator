package migrator.app.domain.project.action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;
import migrator.lib.storage.Storage;

public class ProjectNewHandlerTest {
    private ProjectService projectService;
    private EventDispatcher dispatcher;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.container = new MockContainerProjectStorage();
        this.projectService = new ProjectService(container);
        this.dispatcher = container.dispatcher();
    }

    @Test
    public void onProjectNew__addToList() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals(1, this.container.projectStore().getList().size());
    }

    @Test
    public void onProjectNew__ProjectHasNameNewProject() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("new_project", this.container.projectStore().getList().get(0).getName());
    }

    @Test
    public void onProjectNew__ProjectHasFolderEmpty() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("", this.container.projectStore().getList().get(0).getFolder());
    }

    @Test
    public void onProjectNew__ProjectHasOutputTypeEmpty() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("", this.container.projectStore().getList().get(0).getOutputType());
    }

    @Test
    public void onProjectNew__ProjectHasDatabaseDbName() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("db_name", this.container.projectStore().getList().get(0).getDatabase().getDatabase());
    }

    @Test
    public void onProjectNew__ProjectHasUserUser() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("user", this.container.projectStore().getList().get(0).getDatabase().getConnection().getUser());
    }

    @Test
    public void onProjectNew__ProjectHasPasswordEmpty() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("", this.container.projectStore().getList().get(0).getDatabase().getConnection().getPassword());
    }

    @Test
    public void onProjectNew__ProjectHasDriverMysql() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("mysql", this.container.projectStore().getList().get(0).getDatabase().getConnection().getDriver());
    }

    @Test
    public void onProjectNew__ProjectHasHostLocalhost() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("localhost", this.container.projectStore().getList().get(0).getDatabase().getConnection().getHost());
    }

    @Test
    public void onProjectNew__ProjectHasPort3306() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals("3306", this.container.projectStore().getList().get(0).getDatabase().getConnection().getPort());
    }

    @Test
    public void onProjectNew__newProjectSelected() {
        this.projectService.start();

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.new")
        );

        assertEquals(this.container.projectStore().getList().get(0), this.container.projectStore().getSelected().get());
    }

    public class MockContainerProjectStorage extends Container {
        @Override
        public Storage<Collection<Project>> projectStorage() {
            return new MockStorage<>();
        }
    }

    public class MockStorage<T> implements Storage<T> {
        @Override
        public void clear() {}

        @Override
        public T load() {
            return null;
        }

        @Override
        public void store(T item) {}
    }
}