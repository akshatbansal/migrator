package migrator.lib.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringAdapterTest {
    @Test
    public void concretize_null_equalsNull() {
        StringAdapter adapter = new StringAdapter();

        String result = adapter.concretize(null);

        assertNull(result);
    }

    @Test
    public void concretize_value_equalsValue() {
        StringAdapter adapter = new StringAdapter();

        String result = adapter.concretize("value");

        assertEquals("value", result);
    }

    @Test
    public void generalize_null_equalsNull() {
        StringAdapter adapter = new StringAdapter();

        String result = adapter.generalize(null);

        assertNull(result);
    }

    @Test
    public void generalize_value_equalsValue() {
        StringAdapter adapter = new StringAdapter();

        String result = adapter.generalize("value");

        assertEquals("value", result);
    }
}