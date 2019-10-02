package migrator.migration;

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

    // @Test public void testAddingTableChange() {
    //     this.changeService.addTableChange(new TableChange("test_table"));
    //     assertEquals(1, this.changeService.getChanges().size());
    //     assertEquals("test_table", this.changeService.getChanges().get(0).getName());
    // }

    // @Test public void testGetOrCreateTableCreatesAndAddsNewTableChange() {
    //     TableChange tableChange = this.changeService.getOrCreateTable("new_table");

    //     assertEquals(1, this.changeService.getChanges().size());
    //     assertEquals(tableChange, this.changeService.getChanges().get(0));
    // }

    // @Test public void testGetOrCreateTableReturnsExistingTable() {
    //     this.changeService = new ChangeService(
    //         new TableChange("table_name")
    //     );
    //     TableChange tableChange = this.changeService.getOrCreateTable("table_name");

    //     assertEquals(1, this.changeService.getChanges().size());
    //     assertNotNull(tableChange);
    //     assertEquals("table_name", tableChange.getName());
    // }

    // @Test public void testGetTableReturnsNullIfTableNotExists() {
    //     assertNull(this.changeService.getTable("table_name"));
    // }

    // @Test public void testGetTableReturnsTableIfTableExists() {
    //     this.changeService = new ChangeService(
    //         new TableChange("table_name")
    //     );
    //     TableChange tableChange = this.changeService.getTable("table_name");
    //     assertNotNull(tableChange);
    //     assertEquals("table_name", tableChange.getName());
    // }
}