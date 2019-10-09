package migrator.lib.modelstorage;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class SimpleActiveState<T> implements ActiveState<T> {
    protected ObjectProperty<T> activated;
    protected ObjectProperty<T> focused;
    protected ObservableList<T> list;

    public SimpleActiveState() {
        this.activated = new SimpleObjectProperty<>();
        this.focused = new SimpleObjectProperty<>();
        this.list = FXCollections.observableArrayList();

        this.list.addListener((Change<? extends T> change) -> {
            this.onListChange();
        });
    }

    protected void onListChange() {
        if (this.focused.get() == null) {
            return;
        }
        if (this.list.contains(this.focused.get())) {
            return;
        }
        this.blur();
    }

    @Override
    public void activate(T value) {
        this.activated.set(value);
    }

    @Override
    public void deactivate() {
        this.activate(null);
    }

    @Override
    public ObjectProperty<T> getActive() {
        return this.activated;
    }

    @Override
    public void blur() {
        this.focus(null);
    }

    @Override
    public void focus(T value) {
        this.focused.set(value);
    }

    @Override
    public ObjectProperty<T> getFocused() {
        return this.focused;
    }

    @Override
    public ObservableList<T> getList() {
        return this.list;
    }

    @Override
    public void setListAll(List<T> list) {
        this.list.setAll(list);
    }

    @Override
    public void add(T item) {
        this.list.add(item);
    }

    @Override
    public void remove(T item) {
        this.list.remove(item);
    }
}