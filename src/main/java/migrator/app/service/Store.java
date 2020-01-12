package migrator.app.service;

import javafx.collections.ObservableList;

public interface Store<T> {
    public ObservableList<T> getList();
    public void add(T item);
    public void remove(T item);

}