package migrator.migration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TableChangeFactoryTest {
    protected TableChangeFactory tableChangeFactory;

    @BeforeEach
    public void setUp() {
        this.tableChangeFactory = new TableChangeFactory();
    }

    @Test public void testCreateNotChangedHasTableNameNull() {
        TableChange change = this.tableChangeFactory.createNotChanged("table_name");
    
        assertNull(change.getName());
    }

    @Test public void testCreateNotChangedIsNoneType() {
        TableChange change = this.tableChangeFactory.createNotChanged("table_name");
    
        assertNull(change.getCommand().getType());
    }

    @Test public void testCreateNotChangedHasInitializedValuesSetAsOriginal() {
        TableChange change = this.tableChangeFactory.createNotChanged("table_name");
    
        assertEquals("table_name", change.getOriginalName());
    }

    @Test public void testCreateWithCreateChangeByNameHasNameChangedSet() {
        TableChange change = this.tableChangeFactory.createWithCreateChange("table_name");
    
        assertEquals("table_name", change.getName());
    }

    @Test public void testCreateWithCreateChangeByNameIsCreateType() {
        TableChange change = this.tableChangeFactory.createWithCreateChange("table_name");
    
        assertEquals("create", change.getCommand().getType());
    }
}