package migrator.lib.repository;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RepositoryMapIndex<T> implements RepositoryIndex<T> {
    protected Map<String, ObservableList<T>> map;
    protected MapKeyExtractor<T> keyExtractor;

    public RepositoryMapIndex(MapKeyExtractor<T> keyExractor) {
        this.map = new Hashtable<>();
        this.keyExtractor = keyExractor;
    }

    protected ObservableList<T> getOrCreate(String key) {
        if (!this.map.containsKey(key)) {
            this.map.put(key, FXCollections.observableArrayList());
        }
        return this.map.get(key);
    }

    @Override
    public void add(T item) {
        this.getOrCreate(this.keyExtractor.getKeyOf(item)).add(item);
    }

    @Override
    public void addAll(Collection<T> items) {
        for (T item : items) {
            this.add(item);
        }
    }

    @Override
    public void remove(T item) {
        this.getOrCreate(this.keyExtractor.getKeyOf(item)).remove(item);
    }

    @Override
    public ObservableList<T> filter(String key) {
        return this.getOrCreate(key);
    }
}