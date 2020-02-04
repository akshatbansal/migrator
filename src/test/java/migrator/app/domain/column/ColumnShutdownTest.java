package migrator.app.domain.column;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.ApplicationTest;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleColumnProperty;

public class ColumnShutdownTest {
    private ApplicationTest applicationTest;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.applicationTest = new ApplicationTest();
        this.container = this.applicationTest.getContainer();
    }

    private Column createColumn() {
        return new Column(
            "1",
            "1",
            new SimpleColumnProperty(
                "2",
                "column_name",
                "integer",
                "0",
                false,
                "11",
                false,
                "",
                false
            ),
            new SimpleColumnProperty(
                "3",
                "column_name",
                "integer",
                "0",
                false,
                "11",
                false,
                "",
                false
            ),
            new ChangeCommand("1", "none")
        );
    }

    @Test
    public void applicationStop_noTable_savesEmptyJsonList() throws Exception {
        this.applicationTest.getAppService().start();
        this.applicationTest.getAppService().stop();

        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        assertEquals("[]", this.container.filesystem().read(new File(path.toString(), "column.json")));
    }

    @Test
    public void applicationShutdown_hasOneProject_fileContainsOneTable() throws Exception {
        this.applicationTest.getAppService().start();
        this.container.columnContainer().columnRepository().addWith(
            this.createColumn()
        );
        this.applicationTest.getAppService().stop();

        Path path = Paths.get(System.getProperty("user.home"), ".migrator");
        assertEquals(
            "[{\"tableId\":\"1\",\"id\":\"1\",\"originalId\":\"2\",\"changeId\":\"1\",\"changeCommandId\":\"1\"}]",
            this.container.filesystem().read(new File(path.toString(), "column.json"))
        );
    }
}