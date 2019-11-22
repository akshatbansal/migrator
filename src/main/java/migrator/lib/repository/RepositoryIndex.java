package migrator.lib.repository;

import java.util.Collection;

import javafx.collections.ObservableList;

public interface RepositoryIndex<T> {
    public void add(T item);
    public void addAll(Collection<T> items);
    public void remove(T item);
    public ObservableList<T> filter(String key);
}