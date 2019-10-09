package migrator.lib.modelstorage;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public interface ActiveState<T> {
    public ObjectProperty<T> getActive();
    public void activate(T value);
    public void deactivate();

    public ObjectProperty<T> getFocused();
    public void focus(T value);
    public void blur();

    public ObservableList<T> getList();
    public void setListAll(List<T> list);
    public void add(T item);
    public void remove(T item);
}