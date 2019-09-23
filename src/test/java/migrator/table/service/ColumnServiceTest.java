package migrator.table.service;

import org.junit.jupiter.api.Test;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;
import migrator.migration.ChangeCommand;
import migrator.migration.ColumnChange;
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

    @Test public void testCreateColumnWithAllValues() {
        ColumnChange change = new ColumnChange("column_name", new ChangeCommand());
        Column column = this.columnService.create(
            "column_name",
            "int",
            "100",
            true,
            change
        );

        assertEquals("column_name", column.getName());
        assertEquals("int", column.getFormat());
        assertEquals("100", column.getDefaultValue());
        assertEquals(true, column.isNullEnabled());
        assertEquals(change, column.getChange());
    }

    @Test public void testCreateColumnWithNameAndChangeSetsDefaultValues() {
        ColumnChange change = new ColumnChange("column_name", new ChangeCommand());
        Column column = this.columnService.create(
            "column_name",
            change
        );

        assertEquals("column_name", column.getName());
        assertEquals("string", column.getFormat());
        assertEquals("", column.getDefaultValue());
        assertEquals(false, column.isNullEnabled());
        assertEquals(change, column.getChange());
    }

    @Test public void testCreateColumnWithNameSetsDefaultValuesAndEmptyChangeCommand() {
        Column column = this.columnService.create(
            "column_name"
        );

        assertEquals("column_name", column.getName());
        assertEquals("string", column.getFormat());
        assertEquals("", column.getDefaultValue());
        assertEquals(false, column.isNullEnabled());
        assertEquals("column_name", column.getChange().getName());
        assertEquals(null, column.getChange().getCommand().getType());
    }

    @Test public void testSelectSetsSelectedValue() {
        this.columnService.select(
            this.columnService.create("id")
        );

        assertEquals("id", this.columnService.getSelected().get().getName());
    }

    @Test public void testAddAddsColumnToList() {
        this.columnService.add(
            this.columnService.create("id")
        );

        assertEquals(1, this.columnService.getList().size());
        assertEquals("id", this.columnService.getList().get(0).getName());
    }

    @Test public void testRemoveRemovesColumnFromList() {
        Column column = this.columnService.create("id");
        this.columnService.add(column);
        this.columnService.remove(column);

        assertEquals(0, this.columnService.getList().size());
    }

    @Test public void testSetAllSetsListValues() {
        Column column = this.columnService.create("id");
        this.columnService.setAll(Arrays.asList(column));

        assertEquals(1, this.columnService.getList().size());
        assertEquals("id", this.columnService.getList().get(0).getName());
    }
}