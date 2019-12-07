package migrator.lib.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class SimpleJsonListAdapterTest {
    @Test
    public void concretize_null_equalsEmptyArray() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        Collection<MockModel> result = adapter.concretize(null);

        assertTrue(result.isEmpty());
    }

    @Test
    public void concretize_emptyString_equalsEmptyArray() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        Collection<MockModel> result = adapter.concretize("");

        assertTrue(result.isEmpty());
    }

    @Test
    public void concretize_oneItem_containsOneItem() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        Collection<MockModel> result = adapter.concretize("[{\"name\":\"peter\"}]");

        assertEquals(1, result.size());
    }

    @Test
    public void concretize_oneItem_hasCorrectName() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        Collection<MockModel> result = adapter.concretize("[{\"name\":\"peter\"}]");

        assertEquals("peter", result.toArray(new MockModel[]{})[0].getName());
    }

    @Test
    public void concretize_twoItems_hasTwoItems() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        Collection<MockModel> result = adapter.concretize("[{\"name\":\"peter\"},{\"name\":\"veroniqua\"}]");

        assertEquals(2, result.size());
    }

    @Test
    public void concretize_twoItems_secondHasCorrectName() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        Collection<MockModel> result = adapter.concretize("[{\"name\":\"peter\"},{\"name\":\"veroniqua\"}]");

        assertEquals("veroniqua", result.toArray(new MockModel[]{})[1].getName());
    }

    @Test
    public void generalize_null_equalsEmptyArrayJson() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        String result = adapter.generalize(null);

        assertEquals("[]", result);
    }

    @Test
    public void generalize_emptyArray_equalsEmptyArrayJson() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        String result = adapter.generalize(new ArrayList<>());

        assertEquals("[]", result);
    }

    @Test
    public void generalize_oneItem_equalsOneItemJson() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        String result = adapter.generalize(Arrays.asList(
            new MockModel("peter")
        ));

        assertEquals("[{\"name\":\"peter\"}]", result);
    }

    @Test
    public void generalize_twoItems_equalsTwoItemsJson() {
        SimpleJsonListAdapter<MockModel> adapter = new SimpleJsonListAdapter<>(new MockModelAdapter());

        String result = adapter.generalize(Arrays.asList(
            new MockModel("peter"),
            new MockModel("veroniqua")
        ));

        assertEquals("[{\"name\":\"peter\"},{\"name\":\"veroniqua\"}]", result);
    }

    public class MockModelAdapter implements Adapter<MockModel, JSONObject> {
        @Override
        public MockModel concretize(JSONObject item) {
            return new MockModel(item.getString("name"));
        }

        @Override
        public JSONObject generalize(MockModel item) {
            JSONObject json = new JSONObject();
            json.put("name", item.getName());
            return json;
        }
    }

    public class MockModel {
        protected String name;

        public MockModel(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}