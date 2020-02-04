package migrator.app.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.ApplicationTest;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.project.model.Project;

public class ProjectShutdownTest {
    private ApplicationTest applicationTest;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.applicationTest = new ApplicationTest();
        this.container = this.applicationTest.getContainer();
    }

    private Project createProject() {
        return new Project(
            new DatabaseConnection(
                new Connection("name", "user", "", "localhost", "3306", "mysql"), "database"
            ),
            "1",
            "project-name",
            "phinx",
            "folder"
        );
    }

    @Test
    public void applicationShutdown_noProject_emptyJsonListInFile() throws Exception {
        this.applicationTest.getAppService().start();
        this.applicationTest.getAppService().stop();

        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        assertEquals("[]", this.container.filesystem().read(new File(path.toString(), "project.json")));
    }

    @Test
    public void applicationShutdown_hasOneProject_fileContainsOneProject() throws Exception {
        this.applicationTest.getAppService().start();
        this.container.projectStore().add(
            this.createProject()
        );
        this.applicationTest.getAppService().stop();

        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        assertEquals(
            "[{\"output\":\"phinx\",\"database\":\"database\",\"connection.name\":\"name\",\"password\":\"\",\"folder\":\"folder\",\"driver\":\"mysql\",\"port\":\"3306\",\"name\":\"project-name\",\"host\":\"localhost\",\"id\":\"1\",\"user\":\"user\"}]",
            this.container.filesystem().read(new File(path.toString(), "project.json"))
        );
    }
}