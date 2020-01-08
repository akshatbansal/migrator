package migrator.app.domain.column;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.database.column.format.SimpleAppColumnFormat;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.table.model.Column;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.lib.uid.SessionIncrementalGenerator;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ColumnFactoryTest {
    protected ColumnFactory columnFactory;

    @BeforeEach
    public void setUp() {
        ObservableMap<String, ApplicationColumnFormat> appFormats = FXCollections.observableHashMap();
        appFormats.put("string", new SimpleAppColumnFormat(false, false, false, false));
        ColumnFormatCollection columnFormatCollection = new ColumnFormatCollection(appFormats);
        this.columnFactory = new ColumnFactory(
            new SessionIncrementalGenerator("ColumnFactory"),
            columnFormatCollection
        );
    }

    @Test public void testCreateNotChangedHasChangedValuesEqualToOriginal() {
        Column column = this.columnFactory.createNotChanged("1", "column_name", "string", "default", true, "255", true, "", false);
    
        assertEquals("column_name", column.getChange().getName());
        assertEquals("string", column.getChange().getFormat());
        assertEquals("default", column.getChange().getDefaultValue());
        assertEquals(true, column.getChange().isNullEnabled());
    }

    @Test public void testCreateNotChangedIsNoneType() {
        Column column = this.columnFactory.createNotChanged("1", "column_name", "string", "default", true, "255", true, "", false);
    
        assertEquals("", column.getChange().getCommand().getType());
    }

    @Test public void testCreateNotChangedHasInitializedValuesSetAsOriginal() {
        Column column = this.columnFactory.createNotChanged("1","column_name", "string", "default", true, "255", true, "", false);

        assertEquals("", column.getChange().getCommand().getType());
        assertEquals("column_name", column.getOriginalName());
        assertEquals("string", column.getOriginalFormat());
        assertEquals("default", column.getOriginalDefaultValue());
        assertTrue(column.isNullEnabledOriginal());
    }

    @Test public void testCreateNotChangedHasInitializedValuesSetAsCurrent() {
        Column column = this.columnFactory.createNotChanged("1", "column_name", "string", "default", true, "255", true, "", false);

        assertEquals("", column.getChange().getCommand().getType());
        assertEquals("column_name", column.getName());
        assertEquals("string", column.getFormat());
        assertEquals("default", column.getDefaultValue());
        assertTrue(column.isNullEnabled());
    }

    @Test public void testCreateWithCreateChangeByNameHasSetValuesInChangeCommand() {
        Column column = this.columnFactory.createWithCreateChange("1", "column_name");
    
        assertEquals("column_name", column.getChange().getName());        
    }

    @Test public void testCreateWithCreateChangeByNameIsCreateType() {
        Column column = this.columnFactory.createWithCreateChange("1", "column_name");
    
        assertEquals("create", column.getChange().getCommand().getType());
    }
}