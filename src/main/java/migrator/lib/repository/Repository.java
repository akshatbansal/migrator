package migrator.lib.repository;

import java.util.Collection;

public interface Repository<T> {
    public T find(String id);
    public void add(T item);
    public void addAll(Collection<T> items);
    public void remove(T item);
    public Collection<T> getAll();
}