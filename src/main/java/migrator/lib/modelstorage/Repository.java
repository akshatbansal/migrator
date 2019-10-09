package migrator.lib.modelstorage;

import java.util.List;

import javafx.collections.ObservableList;

public interface Repository<T> {
    public ObservableList<T> getList(String key);
    public void setList(String key, List<T> list);
    public void add(String key, T value);
    public T get(String key, String id);
    public T getOrCreate(String key, T value);
    public void remove(String key, T value);
    public void removeAll(String key);
}