package migrator.app.domain.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.ApplicationTest;
import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleIndexProperty;

public class IndexShutdownTest {
    private ApplicationTest applicationTest;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.applicationTest = new ApplicationTest();
        this.container = this.applicationTest.getContainer();
    }

    private Index createIndex() {
        return new Index(
            "1",
            "1",
            new SimpleIndexProperty(
                "2",
                "index_name",
                new LinkedList<>()
            ),
            new SimpleIndexProperty(
                "3",
                "index_name",
                new LinkedList<>()
            ),
            new ChangeCommand("1", "none")
        );
    }

    @Test
    public void applicationStop_noTable_savesEmptyJsonList() throws Exception {
        this.applicationTest.getAppService().start();
        this.applicationTest.getAppService().stop();

        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        assertEquals("[]", this.container.filesystem().read(new File(path.toString(), "index.json")));
    }

    @Test
    public void applicationShutdown_hasOneProject_fileContainsOneTable() throws Exception {
        this.applicationTest.getAppService().start();
        this.container.indexContainer().indexRepository().addWith(
            this.createIndex()
        );
        this.applicationTest.getAppService().stop();

        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        assertEquals(
            "[{\"tableId\":\"1\",\"id\":\"1\",\"originalId\":\"2\",\"changeId\":\"1\",\"changeCommandId\":\"1\"}]",
            this.container.filesystem().read(new File(path.toString(), "index.json"))
        );
    }
}