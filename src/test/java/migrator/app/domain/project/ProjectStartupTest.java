package migrator.app.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.ApplicationTest;

public class ProjectStartupTest {
    private ApplicationTest applicationTest;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.applicationTest = new ApplicationTest();
        this.container = this.applicationTest.getContainer();
    }

    @Test
    public void applicationStartup_noFile_emptyList() {
        this.applicationTest.getAppService().start();

        assertEquals(0, container.projectStore().getList().size());
    }

    @Test
    public void applicationStartup_containsOneProject_listHasOneItem() throws Exception {
        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        this.container.filesystem().write(
            new File(path.toString(), "project.json"),
            "[{\"output\":\"phinx\",\"database\":\"migrator\",\"connection.name\":\"new_project\",\"password\":\"\",\"folder\":\"/home/arksys/Downloads/ovaldo/migrations\",\"driver\":\"mysql\",\"port\":\"3306\",\"name\":\"mysql-test\",\"host\":\"localhost\",\"id\":\"1579549535795-1\",\"user\":\"user\"}]"
        );
        this.applicationTest.getAppService().start();

        assertEquals(1, container.projectStore().getList().size());
    }
}