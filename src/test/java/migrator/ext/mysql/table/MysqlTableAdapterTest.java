package migrator.ext.mysql.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableProperty;

public class MysqlTableAdapterTest {
    @Test public void generalize_null_isNull() {
        MysqlTableAdapter adapter = new MysqlTableAdapter();

        TableProperty result = adapter.generalize(null);

        assertNull(result);
    }

    @Test public void generalize_emptyString_isNull() {
        MysqlTableAdapter adapter = new MysqlTableAdapter();

        TableProperty result = adapter.generalize("");

        assertNull(result);
    }

    @Test public void generalize_string_hasName() {
        MysqlTableAdapter adapter = new MysqlTableAdapter();

        TableProperty result = adapter.generalize("table_name");

        assertEquals("table_name", result.getName());
    }

    @Test public void concretize_null_isEmptyString() {
        MysqlTableAdapter adapter = new MysqlTableAdapter();

        String result = adapter.concretize(null);

        assertEquals("", result);
    }

    @Test public void concretize_tableProperty_equalsName() {
        MysqlTableAdapter adapter = new MysqlTableAdapter();

        String result = adapter.concretize(new SimpleTableProperty("", "table_name"));

        assertEquals("table_name", result);
    }
}