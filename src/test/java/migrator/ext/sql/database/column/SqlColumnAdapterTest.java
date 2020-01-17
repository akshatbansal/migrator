package migrator.ext.sql.database.column;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.Test;

import migrator.app.migration.model.ColumnProperty;
import migrator.lib.adapter.Adapter;

public class SqlColumnAdapterTest {
    private Map<String, String> createDbColumn(
        String name,
        String format,
        String nullEnabled
    ) {
        Map<String, String> dbColumn = new Hashtable<>();
        dbColumn.put("name", name);
        dbColumn.put("format", format);
        dbColumn.put("nullEnabled", nullEnabled);

        return dbColumn;
    }

    @Test public void concretize_null_isNull() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        ColumnProperty result = adapter.concretize(null);

        assertNull(result);
    }

    @Test public void concretize_column_hasName() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "varchar(10)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("column_name", result.nameProperty().get());
    }

    @Test public void concretize_column_hasNullEnabled() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "varchar(10)", "YES");        
        ColumnProperty result = adapter.concretize(dbColumn);

        assertTrue(result.nullProperty().getValue());
    }

    @Test public void concretize_columnVarchar_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "varchar(10)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("string", result.formatProperty().get());
    }

    @Test public void concretize_columnInt_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "int(11)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("integer", result.formatProperty().get());
    }

    @Test public void concretize_columnTinyint_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "tinyint(11)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("boolean", result.formatProperty().get());
    }

    @Test public void concretize_columnDouble_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "double(11,2)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("decimal", result.formatProperty().get());
    }

    @Test public void concretize_columnChar_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "char(50)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("char", result.formatProperty().get());
    }

    @Test public void concretize_columnBigint_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "bigint(20)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("long", result.formatProperty().get());
    }

    @Test public void concretize_columnTimestamp_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "timestamp", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("timestamp", result.formatProperty().get());
    }

    @Test public void concretize_columnDate_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "date", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("date", result.formatProperty().get());
    }

    @Test public void concretize_columnDatetime_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "datetime", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("datetime", result.formatProperty().get());
    }

    @Test public void concretize_columnTime_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "time", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("time", result.formatProperty().get());
    }

    @Test public void concretize_columnText_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "text", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("text", result.formatProperty().get());
    }

    @Test public void concretize_columnFloat_hasFormatInApplicationForm() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "float(8, 4)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("float", result.formatProperty().get());
    }

    @Test public void concretize_columnUnknown_hasEmptyFormat() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "unknown(8, 4)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("", result.formatProperty().get());
    }

    @Test public void concretize_columnVarchar_hasFormatLength() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "varchar(11)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("11", result.lengthProperty().get());
    }

    @Test public void concretize_columnChar_hasFormatLength() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "char(50)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("50", result.lengthProperty().get());
    }

    @Test public void concretize_columnBigint_hasFormatLength() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "bigint(20)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("20", result.lengthProperty().get());
    }

    @Test public void concretize_columnDouble_hasFormatLength() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "double(11,2)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("11", result.lengthProperty().get());
    }

    @Test public void concretize_columnFloat_hasFormatLength() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "float(8,4)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("8", result.lengthProperty().get());
    }

    @Test public void concretize_columnDouble_hasFormatPrecision() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "double(11,2)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("2", result.precisionProperty().get());
    }

    @Test public void concretize_columnFloat_hasFormatPrecision() {
        Adapter<ColumnProperty, Map<String, String>> adapter = new SqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "float(8,4)", "YES");
        ColumnProperty result = adapter.concretize(dbColumn);

        assertEquals("4", result.precisionProperty().get());
    }
} 