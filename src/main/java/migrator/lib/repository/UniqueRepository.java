package migrator.lib.repository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class UniqueRepository<T extends UniqueItem> implements Repository<T> {
    protected Map<String, T> table;

    public UniqueRepository() {
        this.table = new LinkedHashMap<>();
    }

    @Override
    public void add(T item) {
        this.table.put(item.getUniqueKey(), item);
    }

    @Override
    public void addAll(Collection<T> items) {
        if (items == null) {
            return;
        }
        for (T item : items) {
            this.table.put(item.getUniqueKey(), item);
        }
    }

    @Override
    public T find(String id) {
        return this.table.get(id);
    }

    @Override
    public void remove(T item) {
        this.table.remove(item.getUniqueKey());
    }

    @Override
    public Collection<T> getAll() {
        return this.table.values();
    }
}