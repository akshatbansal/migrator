package migrator.lib.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ListAdapterTest {
    private Adapter<List<String>, List<String>> createAdapter() {
        return new ListAdapter<>(
            new CaseAdapter()
        );
    }

    @Test public void generalize_null_isEmptyList() {
        Adapter<List<String>, List<String>> adapter = this.createAdapter();

        List<String> result = adapter.generalize(null);

        assertEquals(0, result.size());
    }

    @Test public void generalize_hasItem_isUpperCase() {
        Adapter<List<String>, List<String>> adapter = this.createAdapter();

        List<String> result = adapter.generalize(Arrays.asList("test"));

        assertEquals("TEST", result.get(0));
    }

    @Test public void concretize_null_isEmptyList() {
        Adapter<List<String>, List<String>> adapter = this.createAdapter();

        List<String> result = adapter.concretize(null);

        assertEquals(0, result.size());
    }

    @Test public void concretize_hasItem_isLowerCase() {
        Adapter<List<String>, List<String>> adapter = this.createAdapter();

        List<String> result = adapter.concretize(Arrays.asList("TEST"));

        assertEquals("test", result.get(0));
    }

    public class CaseAdapter implements Adapter<String, String> {
        @Override
        public String concretize(String item) {
            return item.toLowerCase();
        }

        @Override
        public String generalize(String item) {
            return item.toUpperCase();
        }
    }
}