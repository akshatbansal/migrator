package migrator.app.domain.change.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeServiceTest {
    protected ChangeService changeService;

    @BeforeEach
    public void setUp() {
        this.changeService = new ChangeService(
            new TableChangeFactory()
        );
    }

    @Test public void testAddingTableChange() {
        this.changeService.addTableChange(
            "localhost",
            this.changeService.getTableChangeFactory()
                .createNotChanged("test_table")
        );
        assertEquals(1, this.changeService.getTables("localhost").size());
        assertEquals("test_table", this.changeService.getTables("localhost").get(0).getOriginalName());
    }

    @Test public void testNonExistingDatabaseKeyReturnsEmptyTableChangesArray() {
        assertEquals(0, this.changeService.getTables("localhost").size());
    }

    @Test public void testGetTableChangePicksTableByOriginalName() {
        this.changeService.addTableChange(
            "localhost",
            this.changeService.getTableChangeFactory()
                .createNotChanged("test_table")
        );

        assertNotNull(this.changeService.getTableChange("localhost", "test_table"));
        assertEquals("test_table", this.changeService.getTableChange("localhost", "test_table").getOriginalName());
    }

    @Test public void testGetCreatedTableChangesReturnsOnlyChangesWithTypeCreated() {
        this.changeService.addTableChange(
            "localhost",
            this.changeService.getTableChangeFactory()
                .createNotChanged("test_table")
        );
        this.changeService.addTableChange(
            "localhost",
            this.changeService.getTableChangeFactory()
                .createWithCreateChange("test_table_two")
        );

        assertEquals(1, this.changeService.getCreatedTableChanges("localhost").size());
        assertEquals("test_table_two", this.changeService.getCreatedTableChanges("localhost").get(0).getOriginalName());
        assertEquals("create", this.changeService.getCreatedTableChanges("localhost").get(0).getCommand().getType());
    }
}