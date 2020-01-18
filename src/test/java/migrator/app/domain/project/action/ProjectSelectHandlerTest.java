package migrator.app.domain.project.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class ProjectSelectHandlerTest {
    private ProjectService projectService;
    private EventDispatcher dispatcher;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.container = new Container();
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
    public void onProjectSelect_whenProjectDeselected_projectStoreSelectedHasValue() {
        this.projectService.start();

        Project project = this.createProject();
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.select", project)
        );

        assertEquals(
            project,
            this.container.projectStore().getSelected().get()
        );
    }

    @Test
    public void onProjectSelect_whenProjectSelected_projectStoreSelectedHasValue() {
        this.projectService.start();
        this.container.projectStore().getSelected().set(
            this.createProject()
        );

        Project project = this.createProject();
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.select", project)
        );

        assertEquals(
            project,
            this.container.projectStore().getSelected().get()
        );
    }

    @Test
    public void onProjectSelectNull_whenProjectSelected_projectStoreSelectedIsNull() {
        this.projectService.start();
        this.container.projectStore().getSelected().set(
            this.createProject()
        );

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.select", null)
        );

        assertNull(
            this.container.projectStore().getOpened().get()
        );
    }
}