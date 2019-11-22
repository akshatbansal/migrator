package migrator.lib.storage;

import migrator.lib.adapter.Adapter;

public class PersistantStorage<T> implements Storage<T> {
    protected Storage<String> storageType;
    protected Adapter<T, String> adapter;

    public PersistantStorage(Storage<String> storageType, Adapter<T, String> adapter) {
        this.storageType = storageType;
        this.adapter = adapter;
    }

    @Override
    public void clear() {
        this.storageType.clear();
    }

    @Override
    public T load() {
        String stringItem = this.storageType.load();
        if (stringItem == null) {
            return null;
        }
        return this.adapter.concretize(stringItem);
    }

    @Override
    public void store(T item) {
        String storeString = null;
        if (item != null) {
            storeString = this.adapter.generalize(item);
        }
        
        this.storageType.store(storeString);
    }
}