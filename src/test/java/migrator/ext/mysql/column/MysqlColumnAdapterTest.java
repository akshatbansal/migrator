package migrator.ext.mysql.column;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.Test;

import migrator.app.migration.model.ColumnProperty;
import migrator.lib.adapter.Adapter;

public class MysqlColumnAdapterTest {
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

    @Test public void generalize_null_isNull() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        ColumnProperty result = adapter.generalize(null);

        assertNull(result);
    }

    @Test public void generalize_column_hasName() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "varchar(10)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("column_name", result.nameProperty().get());
    }

    @Test public void generalize_column_hasNullEnabled() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "varchar(10)", "YES");        
        ColumnProperty result = adapter.generalize(dbColumn);

        assertTrue(result.nullProperty().getValue());
    }

    @Test public void generalize_columnVarchar_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "varchar(10)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("string", result.formatProperty().get());
    }

    @Test public void generalize_columnInt_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "int(11)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("integer", result.formatProperty().get());
    }

    @Test public void generalize_columnTinyint_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "tinyint(11)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("boolean", result.formatProperty().get());
    }

    @Test public void generalize_columnDouble_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "double(11,2)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("decimal", result.formatProperty().get());
    }

    @Test public void generalize_columnChar_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "char(50)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("char", result.formatProperty().get());
    }

    @Test public void generalize_columnBigint_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "bigint(20)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("long", result.formatProperty().get());
    }

    @Test public void generalize_columnTimestamp_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "timestamp", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("timestamp", result.formatProperty().get());
    }

    @Test public void generalize_columnDate_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "date", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("date", result.formatProperty().get());
    }

    @Test public void generalize_columnDatetime_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "datetime", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("datetime", result.formatProperty().get());
    }

    @Test public void generalize_columnTime_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "time", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("time", result.formatProperty().get());
    }

    @Test public void generalize_columnText_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "text", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("text", result.formatProperty().get());
    }

    @Test public void generalize_columnFloat_hasFormatInApplicationForm() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "float(8, 4)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("float", result.formatProperty().get());
    }

    @Test public void generalize_columnVarchar_hasFormatLength() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "varchar(11)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("11", result.lengthProperty().get());
    }

    @Test public void generalize_columnChar_hasFormatLength() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "char(50)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("50", result.lengthProperty().get());
    }

    @Test public void generalize_columnBigint_hasFormatLength() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "bigint(20)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("20", result.lengthProperty().get());
    }

    @Test public void generalize_columnDouble_hasFormatLength() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "double(11,2)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("11", result.lengthProperty().get());
    }

    @Test public void generalize_columnFloat_hasFormatLength() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "float(8,4)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("8", result.lengthProperty().get());
    }

    @Test public void generalize_columnDouble_hasFormatPrecision() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "double(11,2)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("2", result.precisionProperty().get());
    }

    @Test public void generalize_columnFloat_hasFormatPrecision() {
        Adapter<Map<String, String>, ColumnProperty> adapter = new MysqlColumnAdapter();

        Map<String, String> dbColumn = this.createDbColumn("column_name", "float(8,4)", "YES");
        ColumnProperty result = adapter.generalize(dbColumn);

        assertEquals("4", result.precisionProperty().get());
    }
}