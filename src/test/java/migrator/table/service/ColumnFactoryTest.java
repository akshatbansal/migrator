package migrator.table.service;

import org.junit.jupiter.api.Test;

import migrator.table.model.Column;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ColumnFactoryTest {
    protected ColumnFactory columnFactory;

    @BeforeEach
    public void setUp() {
        this.columnFactory = new ColumnFactory();
    }

    @Test public void testCreateNotChangedHasNullValuesInChangeCommand() {
        Column column = this.columnFactory.createNotChanged("column_name", "format", "default", true);
    
        assertNull(column.getChange().getName());
        assertNull(column.getChange().getFormat());
        assertNull(column.getChange().getDefaultValue());
        assertNull(column.getChange().isNullEnabled());
    }

    @Test public void testCreateNotChangedIsNoneType() {
        Column column = this.columnFactory.createNotChanged("column_name", "format", "default", true);
    
        assertNull(column.getChange().getCommand().getType());
    }

    @Test public void testCreateNotChangedHasInitializedValuesSetAsOriginal() {
        Column column = this.columnFactory.createNotChanged("column_name", "format", "default", true);

        assertNull(column.getChange().getCommand().getType());
        assertEquals("column_name", column.getOriginalName());
        assertEquals("format", column.getOriginalFormat());
        assertEquals("default", column.getOriginalDefaultValue());
        assertTrue(column.isNullEnabledOriginal());
    }

    @Test public void testCreateNotChangedHasInitializedValuesSetAsCurrent() {
        Column column = this.columnFactory.createNotChanged("column_name", "format", "default", true);

        assertNull(column.getChange().getCommand().getType());
        assertEquals("column_name", column.getName());
        assertEquals("format", column.getFormat());
        assertEquals("default", column.getDefaultValue());
        assertTrue(column.isNullEnabled());
    }

    @Test public void testCreateWithCreateChangeByNameHasNullValuesAsValuesInChangeCommand() {
        Column column = this.columnFactory.createWithCreateChange("column_name");
    
        assertNull(column.getChange().getName());
        assertNull(column.getChange().getFormat());
        assertNull(column.getChange().getDefaultValue());
        assertNull(column.getChange().isNullEnabled());
    }

    @Test public void testCreateWithCreateChangeByNameIsCreateType() {
        Column column = this.columnFactory.createWithCreateChange("column_name");
    
        assertEquals("create", column.getChange().getCommand().getType());
    }
}