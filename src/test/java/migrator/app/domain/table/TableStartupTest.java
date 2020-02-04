package migrator.app.domain.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.ApplicationTest;

public class TableStartupTest {
    private ApplicationTest applicationTest;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.applicationTest = new ApplicationTest();
        this.container = this.applicationTest.getContainer();
    }

    @Test
    public void applicationStart_noFile_hasZeroItemsInTableRepository() {
        this.applicationTest.getAppService().start();

        assertEquals(0, container.tableContainer().tableRepository().findByProject("1").size());
    }

    @Test
    public void applicationStartup_containsOneTable_repositoryHasOneItem() throws Exception {
        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        this.container.filesystem().write(
            new File(path.toString(), "change_command.json"),
            "[{\"id\":\"4\",\"type\":\"none\"}]"
        );
        this.container.filesystem().write(
            new File(path.toString(), "table_property.json"),
            "[{\"id\":\"1\",\"name\":\"table_name\"},{\"id\":\"3\",\"name\":\"table_name\"}]"
        );
        this.container.filesystem().write(
            new File(path.toString(), "table.json"),
            "[{\"id\":\"1\",\"originalId\":\"2\",\"changeId\":\"1\",\"projectId\":\"1\",\"changeCommandId\":\"4\"}]"
        );
        this.applicationTest.getAppService().start();

        assertEquals(1, container.tableContainer().tableRepository().findByProject("1").size());
    }
}