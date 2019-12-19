package migrator.ext.mysql.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.lib.adapter.Adapter;

public class MysqlIndexAdapterTest {
    private Adapter<List<String>, IndexProperty> createAdapter(List<ColumnProperty> availableColumns) {
        return new MysqlIndexAdapter(availableColumns);
    }

    @Test public void generalize_null_isNull() {
        Adapter<List<String>, IndexProperty> adapter = this.createAdapter(new ArrayList<>());

        IndexProperty result = adapter.generalize(null);

        assertNull(result);
    }

    @Test public void generalize_emptyArray_isNull() {
        Adapter<List<String>, IndexProperty> adapter = this.createAdapter(new ArrayList<>());

        IndexProperty result = adapter.generalize(new ArrayList<>());

        assertNull(result);
    }

    @Test public void generalize_oneColumn_hasFirstArrayIndexName() {
        Adapter<List<String>, IndexProperty> adapter = this.createAdapter(new ArrayList<>());

        IndexProperty result = adapter.generalize(Arrays.asList("index_name", "id"));

        assertEquals("index_name", result.getName());
    }

    @Test public void generalize_oneColumn_hasOneColumn() {
        Adapter<List<String>, IndexProperty> adapter = this.createAdapter(Arrays.asList(
            new SimpleColumnProperty("", "id", "", "", false, "", false, "", false)
        ));

        IndexProperty result = adapter.generalize(Arrays.asList("index_name", "id"));

        assertEquals(1, result.columnsProperty().size());
    }

    @Test public void concretize_null_isNull() {
        Adapter<List<String>, IndexProperty> adapter = this.createAdapter(new ArrayList<>());

        List<String> result = adapter.concretize(null);

        assertNull(result);
    }

    @Test public void concretize_indexProperty_firtsArrayItemIsName() {
        Adapter<List<String>, IndexProperty> adapter = this.createAdapter(new ArrayList<>());

        List<String> result = adapter.concretize(new SimpleIndexProperty("", "index_name", new ArrayList<>()));

        assertEquals("index_name", result.get(0));
    }

    @Test public void concretize_indexProperty_hasOneColumn() {
        Adapter<List<String>, IndexProperty> adapter = this.createAdapter(null);

        List<String> result = adapter.concretize(new SimpleIndexProperty("", "index_name", Arrays.asList(
            new SimpleColumnProperty("", "column_name", "", "", false, "", false, "", false)
        )));

        assertEquals("column_name", result.get(1));
    }
}