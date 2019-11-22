package migrator.lib.storage;

import org.junit.jupiter.api.Test;

import migrator.lib.adapter.Adapter;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PersistantStorageTest {
    @BeforeEach public void setUp() {

    }

    @Test public void testLoadingSimpleString() {
        Storage<String> storage = new PersistantStorage<>(
            new MockPersistantStorageType("initial"),
            new StringAdapter()
        );

        assertEquals("initial", storage.load());
    }

    @Test public void testLoadingSimpleStringUndefined() {
        Storage<String> storage = new PersistantStorage<>(
            new MockPersistantStorageType(null),
            new StringAdapter()
        );

        assertNull(storage.load());
    }

    @Test public void testStoringSimpleString() {
        Storage<String> storage = new PersistantStorage<>(
            new MockPersistantStorageType(null),
            new StringAdapter()
        );

        storage.store("simple");
        assertEquals("simple", storage.load());
    }

    @Test public void testStoringNullString() {
        Storage<String> storage = new PersistantStorage<>(
            new MockPersistantStorageType("init"),
            new StringAdapter()
        );

        storage.store(null);
        assertNull(storage.load());
    }

    @Test public void testClearingSimpleString() {
        Storage<String> storage = new PersistantStorage<>(
            new MockPersistantStorageType(null),
            new StringAdapter()
        );

        storage.store("simple");
        storage.clear();
        assertNull(storage.load());
    }

    @Test public void testLoadingAdapterValue() {
        Storage<MockModel> storage = new PersistantStorage<>(
            new MockPersistantStorageType("john:smith"),
            new MockModelAdapter()
        );

        assertNotNull(storage.load());
        assertEquals("john", storage.load().getName());
        assertEquals("smith", storage.load().getSurname());
    }

    @Test public void testLoadingNullAdapterValue() {
        Storage<MockModel> storage = new PersistantStorage<>(
            new MockPersistantStorageType(null),
            new MockModelAdapter()
        );

        assertNull(storage.load());
    }

    @Test public void testStoringAdapterValue() {
        Storage<MockModel> storage = new PersistantStorage<>(
            new MockPersistantStorageType(null),
            new MockModelAdapter()
        );

        MockModel model = new MockModel("john", "smith");
        storage.store(model);

        assertNotNull(storage.load());
        assertEquals("john", storage.load().getName());
        assertEquals("smith", storage.load().getSurname());
    }

    @Test public void testStoringNullAdapterValue() {
        Storage<MockModel> storage = new PersistantStorage<>(
            new MockPersistantStorageType("john:smith"),
            new MockModelAdapter()
        );

        storage.store(null);
        assertNull(storage.load());
    }

    @Test public void testClearingAdapterValue() {
        Storage<MockModel> storage = new PersistantStorage<>(
            new MockPersistantStorageType(null),
            new MockModelAdapter()
        );

        MockModel model = new MockModel("josh", "smith");
        storage.store(model);
        storage.clear();
        assertNull(storage.load());
    }

    public class MockPersistantStorageType implements Storage<String> {
        protected String storedData;

        public MockPersistantStorageType(String initialValue) {
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