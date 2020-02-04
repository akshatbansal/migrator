package migrator.app.domain.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.ApplicationTest;

public class IndexStartupTest {
    private ApplicationTest applicationTest;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.applicationTest = new ApplicationTest();
        this.container = this.applicationTest.getContainer();
    }

    @Test
    public void applicationStart_noFile_hasZeroItemsInColumnRepository() {
        this.applicationTest.getAppService().start();

        assertEquals(0, container.indexContainer().indexRepository().findByTable("1").size());
    }

    @Test
    public void applicationStartup_containsOneColumn_repositoryHasOneItem() throws Exception {
        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        this.container.filesystem().write(
            new File(path.toString(), "change_command.json"),
            "[{\"id\":\"1\",\"type\":\"none\"}]"
        );
        this.container.filesystem().write(
            new File(path.toString(), "index_property.json"),
            "[{\"columns\":[],\"name\":\"index_name\",\"id\":\"2\"},{\"columns\":[],\"name\":\"index_name\",\"id\":\"3\"}]"
        );
        this.container.filesystem().write(
            new File(path.toString(), "index.json"),
            "[{\"tableId\":\"1\",\"id\":\"1\",\"originalId\":\"1\",\"changeId\":\"2\",\"changeCommandId\":\"1\"}]"
        );
        this.applicationTest.getAppService().start();

        assertEquals(1, container.indexContainer().indexRepository().findByTable("1").size());
    }
}