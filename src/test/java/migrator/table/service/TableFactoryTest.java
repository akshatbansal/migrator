package migrator.migration;

import org.junit.jupiter.api.Test;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;
import migrator.table.model.Table;
import migrator.table.service.TableFactory;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TableFactoryTest {
    protected TableFactory tableFactory;

    @BeforeEach
    public void setUp() {
        this.tableFactory = new TableFactory(
            new TableChangeFactory()
        );
    }

    @Test public void testCreateNotChangedHasTableNameSet() {
        Table table = this.tableFactory.createNotChanged(
            new DatabaseConnection(
                new Connection("localhost"),
                "db_name"
            ),
            "table_name"
        );
    
        assertEquals("table_name", table.getName());
    }

    @Test public void testCreateNotChangedIsNoneType() {
        Table table = this.tableFactory.createNotChanged(
            new DatabaseConnection(
                new Connection("localhost"),
                "db_name"
            ),
            "table_name"
        );
    
        assertNull(table.getChange().getCommand().getType());
    }

    @Test public void testCreateNotChangedHasOriginalValuesSet() {
        Table table = this.tableFactory.createNotChanged(
            new DatabaseConnection(
                new Connection("localhost"),
                "db_name"
            ),
            "table_name"
        );
    
        assertEquals("table_name", table.getOriginalName());
    }

    @Test public void testCreateNotChangedHasChangesSetToNull() {
        Table table = this.tableFactory.createNotChanged(
            new DatabaseConnection(
                new Connection("localhost"),
                "db_name"
            ),
            "table_name"
        );
    
        assertNull(table.getChange().getName());
    }

    @Test public void testCreateWithCreateChangeByNameHasNameSet() {
        Table table = this.tableFactory.createWithCreateChange(
            new DatabaseConnection(
                new Connection("localhost"),
                "db_name"
            ),
            "table_name"
        );
    
        assertEquals("table_name", table.getName());
    }

    @Test public void testCreateWithCreateChangeByNameIsCreateType() {
        Table table = this.tableFactory.createWithCreateChange(
            new DatabaseConnection(
                new Connection("localhost"),
                "db_name"
            ),
            "table_name"
        );
    
        assertEquals("create", table.getChange().getCommand().getType());
    }

    @Test public void testCreateWithCreateChangeByNameHasChangeSet() {
        Table table = this.tableFactory.createWithCreateChange(
            new DatabaseConnection(
                new Connection("localhost"),
                "db_name"
            ),
            "table_name"
        );
    
        assertEquals("table_name", table.getChange().getName());
    }
}