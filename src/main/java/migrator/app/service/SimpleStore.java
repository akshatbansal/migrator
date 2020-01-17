package migrator.app.service;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SimpleStore<T> implements SelectableStore<T> {
    protected ObservableList<T> list;
    protected ObjectProperty<T> selected;

    public SimpleStore() {
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
    }

    @Override
    public void add(T item) {
        this.list.add(item);
    }

    @Override
    public void deselect() {
        this.select(null);
    }

    @Override
    public ObservableList<T> getList() {
        return this.list;
    }

    @Override
    public ObjectProperty<T> getSelected() {
        return this.selected;
    }

    @Override
    public void remove(T item) {
        this.list.remove(item);
    }

    @Override
    public void select(T item) {
        this.selected.set(item);
    }
}