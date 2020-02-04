package migrator.app.domain.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import migrator.app.boot.Container;
import migrator.app.domain.ApplicationTest;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleTableProperty;

public class TableShutdownTest {
    private ApplicationTest applicationTest;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.applicationTest = new ApplicationTest();
        this.container = this.applicationTest.getContainer();
    }

    private Table createTable() {
        return new Table(
            "1",
            "p-1",
            new SimpleTableProperty("2", "table_name"),
            new SimpleTableProperty("3", "table_name"),
            new ChangeCommand("4", "none"),
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList()
        );
    }

    @Test
    public void applicationStop_noTable_savesEmptyJsonList() throws Exception {
        this.applicationTest.getAppService().start();
        this.applicationTest.getAppService().stop();

        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        assertEquals("[]", this.container.filesystem().read(new File(path.toString(), "table.json")));
    }

    @Test
    public void applicationShutdown_hasOneProject_fileContainsOneTable() throws Exception {
        this.applicationTest.getAppService().start();
        this.container.tableContainer().tableRepository().addWith(
            this.createTable()
        );
        this.applicationTest.getAppService().stop();

        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        assertEquals(
            "[{\"id\":\"1\",\"originalId\":\"2\",\"changeId\":\"1\",\"projectId\":\"p-1\",\"changeCommandId\":\"4\"}]",
            this.container.filesystem().read(new File(path.toString(), "table.json"))
        );
    }
}