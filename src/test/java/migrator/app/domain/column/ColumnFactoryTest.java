package migrator.app.domain.column;

import org.junit.jupiter.api.Test;

import migrator.app.database.format.FakeColumnFormatManager;
import migrator.app.database.format.SimpleColumnFormat;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.table.model.Column;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ColumnFactoryTest {
    protected ColumnFactory columnFactory;

    @BeforeEach
    public void setUp() {
        this.columnFactory = new ColumnFactory(
            new FakeColumnFormatManager(
                new SimpleColumnFormat("format")
            )
        );
    }

    @Test public void testCreateNotChangedHasChangedValuesEqualToOriginal() {
        Column column = this.columnFactory.createNotChanged("column_name", "format", "default", true, "255", true, "");
    
        assertEquals("column_name", column.getChange().getName());
        assertEquals("format", column.getChange().getFormat());
        assertEquals("default", column.getChange().getDefaultValue());
        assertEquals(true, column.getChange().isNullEnabled());
    }

    @Test public void testCreateNotChangedIsNoneType() {
        Column column = this.columnFactory.createNotChanged("column_name", "format", "default", true, "255", true, "");
    
        assertEquals("", column.getChange().getCommand().getType());
    }

    @Test public void testCreateNotChangedHasInitializedValuesSetAsOriginal() {
        Column column = this.columnFactory.createNotChanged("column_name", "format", "default", true, "255", true, "");

        assertEquals("", column.getChange().getCommand().getType());
        assertEquals("column_name", column.getOriginalName());
        assertEquals("format", column.getOriginalFormat());
        assertEquals("default", column.getOriginalDefaultValue());
        assertTrue(column.isNullEnabledOriginal());
    }

    @Test public void testCreateNotChangedHasInitializedValuesSetAsCurrent() {
        Column column = this.columnFactory.createNotChanged("column_name", "format", "default", true, "255", true, "");

        assertEquals("", column.getChange().getCommand().getType());
        assertEquals("column_name", column.getName());
        assertEquals("format", column.getFormat());
        assertEquals("default", column.getDefaultValue());
        assertTrue(column.isNullEnabled());
    }

    @Test public void testCreateWithCreateChangeByNameHasSetValuesInChangeCommand() {
        Column column = this.columnFactory.createWithCreateChange("column_name");
    
        assertEquals("column_name", column.getChange().getName());        
    }

    @Test public void testCreateWithCreateChangeByNameIsCreateType() {
        Column column = this.columnFactory.createWithCreateChange("column_name");
    
        assertEquals("create", column.getChange().getCommand().getType());
    }
}