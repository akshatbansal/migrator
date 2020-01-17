package migrator.ext.sql.database.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableProperty;

public class SqlTableAdapterTest {
    @Test public void generalize_null_isNull() {
        SqlTableAdapter adapter = new SqlTableAdapter();

        TableProperty result = adapter.generalize(null);

        assertNull(result);
    }

    @Test public void generalize_emptyString_isNull() {
        SqlTableAdapter adapter = new SqlTableAdapter();

        TableProperty result = adapter.generalize("");

        assertNull(result);
    }

    @Test public void generalize_string_hasName() {
        SqlTableAdapter adapter = new SqlTableAdapter();

        TableProperty result = adapter.generalize("table_name");

        assertEquals("table_name", result.getName());
    }

    @Test public void concretize_null_isEmptyString() {
        SqlTableAdapter adapter = new SqlTableAdapter();

        String result = adapter.concretize(null);

        assertEquals("", result);
    }

    @Test public void concretize_tableProperty_equalsName() {
        SqlTableAdapter adapter = new SqlTableAdapter();

        String result = adapter.concretize(new SimpleTableProperty("", "table_name"));

        assertEquals("table_name", result);
    }
} 