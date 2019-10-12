package migrator.lib.modelstorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

abstract public class ObservableListRepository<T> implements Repository<T> {
    protected Map<String, ObservableList<T>> map;

    public ObservableListRepository() {
        this.map = new HashMap<>();
    }

    protected ObservableList<T> getOrCreateList(String key) {
        if (!this.map.containsKey(key)) {
            this.map.put(key, FXCollections.observableArrayList());
        }
        return this.map.get(key);
    }

    @Override
    public void add(String key, T value) {
        this.getOrCreateList(key).add(value);
    }

    @Override
    public ObservableList<T> getList(String key) {
        return this.getOrCreateList(key);
    }

    @Override
    public void remove(String key, T value) {
        this.getOrCreateList(key).remove(value);
    }

    @Override
    public void removeAll(String key) {
        this.getOrCreateList(key).clear();
    }

    @Override
    public void setList(String key, List<T> list) {
        this.getOrCreateList(key).setAll(list);
    }

    @Override
    public T get(String key, String id) {
        List<T> list = this.getOrCreateList(key);
        for (T item : list) {
            if (this.getId(item).equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public T getOrCreate(String key, T value) {
        T exisitngValue = this.get(key, this.getId(value));
        if (exisitngValue == null) {
            this.add(key, value);
            return value;
        }
        return exisitngValue;
    }

    protected abstract String getId(T value);
}