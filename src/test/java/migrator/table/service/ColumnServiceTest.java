package migrator.table.service;

import org.junit.jupiter.api.Test;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;
import migrator.table.model.Column;
import migrator.table.model.Table;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class ColumnServiceTest {
    protected ColumnService columnService;

    @BeforeEach
    public void setUp() {
        this.columnService = new ColumnService();
    }

    @Test public void testSelectSetsSelectedValue() {
        this.columnService.select(
            new Column("id")
        );

        assertEquals("id", this.columnService.getSelected().get().getName());
    }

    @Test public void testAddAddsColumnToList() {
        this.columnService.add(
            new Column("id")
        );

        assertEquals(1, this.columnService.getList().size());
        assertEquals("id", this.columnService.getList().get(0).getName());
    }

    @Test public void testRemoveRemovesColumnFromList() {
        Column column = new Column("id");
        this.columnService.add(column);
        this.columnService.remove(column);

        assertEquals(0, this.columnService.getList().size());
    }

    @Test public void testSetAllSetsListValues() {
        Column column = new Column("id");
        this.columnService.setAll(Arrays.asList(column));

        assertEquals(1, this.columnService.getList().size());
        assertEquals("id", this.columnService.getList().get(0).getName());
    }
}