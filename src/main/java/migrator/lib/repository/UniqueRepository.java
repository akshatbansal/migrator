package migrator.lib.repository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UniqueRepository<T extends UniqueItem> implements Repository<T> {
    protected Map<String, T> table;
    protected List<RepositoryIndex<T>> indexes;

    public UniqueRepository() {
        this.table = new LinkedHashMap<>();
        this.indexes = new LinkedList<>();
    }

    @Override
    public void add(T item) {
        this.table.put(item.getUniqueKey(), item);
        for (RepositoryIndex<T> index : this.indexes) {
            index.add(item);
        }
    }

    @Override
    public void addAll(Collection<T> items) {
        if (items == null) {
            return;
        }
        for (T item : items) {
            this.table.put(item.getUniqueKey(), item);
        }
        for (RepositoryIndex<T> index : this.indexes) {
            index.addAll(items);
        }
    }

    @Override
    public T find(String id) {
        return this.table.get(id);
    }

    @Override
    public void remove(T item) {
        this.table.remove(item.getUniqueKey());
        for (RepositoryIndex<T> index : this.indexes) {
            index.remove(item);
        }
    }

    @Override
    public void removeAll(Collection<T> items) {
        if (items == null) {
            return;
        }
        for (T item : items) {
            this.table.remove(item.getUniqueKey());
        }
        for (RepositoryIndex<T> index : this.indexes) {
            index.removeAll(items);
        }
    }

    @Override
    public Collection<T> getAll() {
        return this.table.values();
    }
}