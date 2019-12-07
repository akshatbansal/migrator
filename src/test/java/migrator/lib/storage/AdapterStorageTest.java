package migrator.lib.storage;

import org.junit.jupiter.api.Test;

import migrator.lib.adapter.Adapter;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class AdapterStorageTest {
    @BeforeEach public void setUp() {

    }

    @Test public void load_initialContentString_equalsInitial() {
        MockStorageType mockStorageType = new MockStorageType("initial");
        Storage<String> storage = new AdapterStorage<>(
            mockStorageType,
            new MockStringAdapter()
        );

        assertEquals("initial", storage.load());
    }

    @Test public void load_initialContentNull_equalsNull() {
        MockStorageType mockStorageType = new MockStorageType(null);
        Storage<String> storage = new AdapterStorage<>(
            mockStorageType,
            new MockStringAdapter()
        );

        assertNull(storage.load());
    }

    @Test public void store_string_storageContainsValue() {
        MockStorageType mockStorageType = new MockStorageType(null);
        Storage<String> storage = new AdapterStorage<>(
            mockStorageType,
            new MockStringAdapter()
        );

        storage.store("simple");
        assertEquals("simple", mockStorageType.load());
    }

    @Test public void store_null_storageContainsNull() {
        MockStorageType mockStorageType = new MockStorageType("init");
        Storage<String> storage = new AdapterStorage<>(
            mockStorageType,
            new MockStringAdapter()
        );

        storage.store(null);
        assertNull(storage.load());
    }

    @Test public void clear_stoarge_containsNull() {
        MockStorageType mockStorageType = new MockStorageType("simple");
        Storage<String> storage = new AdapterStorage<>(
            mockStorageType,
            new MockStringAdapter()
        );

        storage.clear();

        assertNull(mockStorageType.load());
    }

    @Test public void testLoadingAdapterValue() {
        Storage<MockModel> storage = new AdapterStorage<>(
            new MockStorageType("john:smith"),
            new MockModelAdapter()
        );

        assertNotNull(storage.load());
        assertEquals("john", storage.load().getName());
        assertEquals("smith", storage.load().getSurname());
    }

    @Test public void testLoadingNullAdapterValue() {
        Storage<MockModel> storage = new AdapterStorage<>(
            new MockStorageType(null),
            new MockModelAdapter()
        );

        assertNull(storage.load());
    }

    @Test public void testStoringAdapterValue() {
        Storage<MockModel> storage = new AdapterStorage<>(
            new MockStorageType(null),
            new MockModelAdapter()
        );

        MockModel model = new MockModel("john", "smith");
        storage.store(model);

        assertNotNull(storage.load());
        assertEquals("john", storage.load().getName());
        assertEquals("smith", storage.load().getSurname());
    }

    @Test public void testStoringNullAdapterValue() {
        Storage<MockModel> storage = new AdapterStorage<>(
            new MockStorageType("john:smith"),
            new MockModelAdapter()
        );

        storage.store(null);
        assertNull(storage.load());
    }

    @Test public void testClearingAdapterValue() {
        Storage<MockModel> storage = new AdapterStorage<>(
            new MockStorageType(null),
            new MockModelAdapter()
        );

        MockModel model = new MockModel("josh", "smith");
        storage.store(model);
        storage.clear();
        assertNull(storage.load());
    }

    public class MockStorageType implements Storage<String> {
        protected String storedData;

        public MockStorageType(String initialValue) {
            this.storedData = initialValue;
        }

        @Override
        public void clear() {
            this.storedData = null;
        }

        @Override
        public String load() {
            return this.storedData;
        }

        @Override
        public void store(String item) {
            this.storedData = item;
        }
    }

    public class MockModel {
        protected String name;
        protected String surname;

        public MockModel(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public String getName() {
            return this.name;
        }

        public String getSurname() {
            return this.surname;
        }
    }

    public class MockStringAdapter implements Adapter<String, String> {
        @Override
        public String concretize(String item) {
            return item;
        }

        @Override
        public String generalize(String item) {
            return item;
        }
    }

    public class MockModelAdapter implements Adapter<MockModel, String> {
        @Override
        public MockModel concretize(String item) {
            String[] parts = item.split(":");
            return new MockModel(parts[0], parts[1]);
        }

        @Override
        public String generalize(MockModel item) {
            return item.getName() + ":" + item.getSurname();
        }
    }
}