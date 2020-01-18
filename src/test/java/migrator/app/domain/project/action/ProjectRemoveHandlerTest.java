package migrator.app.domain.project.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;
import migrator.lib.storage.Storage;

public class ProjectRemoveHandlerTest {
    private ProjectService projectService;
    private EventDispatcher dispatcher;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.container = new MockContainerProjectStorage();
        this.projectService = new ProjectService(container);
        this.dispatcher = container.dispatcher();
    }

    private Project createProject() {
        return new Project(
            new DatabaseConnection(
                new Connection("name", "user", "", "localhost", "3303", "driver"), "database"
            ),
            "id",
            "name",
            "dbStructure",
            "folder"
        );
    }

    @Test
    public void onProjectRemove_notInProjectList_projectListIsTheSame() {
        this.projectService.start();
        this.container.projectStore().getList().add(
            this.createProject()
        );

        Project project = this.createProject();
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.remove", project)
        );

        assertEquals(1, this.container.projectStore().getList().size());
    }

    @Test
    public void onProjectRemove_inProjectList_projectListHasOneItemRemoved() {
        this.projectService.start();
        Project project = this.createProject();
        this.container.projectStore().getList().add(project);

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.remove", project)
        );

        assertEquals(0, this.container.projectStore().getList().size());
    }

    @Test
    public void onProjectRemove_isSelected_isDeselected() {
        this.projectService.start();
        Project project = this.createProject();
        this.container.projectStore().getList().add(project);
        this.container.projectStore().getSelected().set(project);

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.remove", project)
        );

        assertNull(this.container.projectStore().getSelected().get());
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