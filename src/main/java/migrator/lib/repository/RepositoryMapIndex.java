package migrator.lib.repository;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    private Map<String, List<T>> separateItems(Collection<T> items) {
        Map<String, List<T>> map = new Hashtable<>();
        for (T item : items) {
            String key = this.keyExtractor.getKeyOf(item);
            if (!map.containsKey(key)) {
                map.put(key, new LinkedList<>());
            }
            map.get(key).add(item);
        }
        return map;
    }

    @Override
    public void addAll(Collection<T> items) {
        Map<String, List<T>> map = this.separateItems(items);

        Iterator<Entry<String, List<T>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, List<T>> entry = iterator.next();
            this.addAll(entry.getKey(), entry.getValue());
        }
    }

    private void addAll(String key, Collection<T> items) {
        this.getOrCreate(key).addAll(items);
    }

    @Override
    public void remove(T item) {
        this.getOrCreate(this.keyExtractor.getKeyOf(item)).remove(item);
    }

    @Override
    public ObservableList<T> filter(String key) {
        return this.getOrCreate(key);
    }

    @Override
    public void removeAll(Collection<T> items) {
        Map<String, List<T>> map = this.separateItems(items);

        Iterator<Entry<String, List<T>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, List<T>> entry = iterator.next();
            this.removeAll(entry.getKey(), entry.getValue());
        }
    }

    private void removeAll(String key, Collection<T> items) {
        this.getOrCreate(key).removeAll(items);
    }
}