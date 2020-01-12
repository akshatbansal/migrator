package migrator.app.service;

import javafx.beans.property.ObjectProperty;

public interface SelectableStore<T> extends Store<T> {
    public void select(T item);
    public void deselect();
    public ObjectProperty<T> getSelected();
}