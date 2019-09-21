package migrator.table.service;

import java.util.Collection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.table.model.Index;

public class IndexService {
    protected ObservableList<Index> list;
    protected ObjectProperty<Index> selected;

    public IndexService() {
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
    }

    public ObservableList<Index> getList() {
        return this.list;
    }

    public ObjectProperty<Index> getSelected() {
        return this.selected;
    }

    public void setAll(Collection<Index> indexes) {
        this.list.setAll(indexes);
    }

    public void select(Index index) {
        this.selected.set(index);
    }

    public void add(Index index) {
        this.list.add(index);
    }

    public void remove(Index index) {
        this.list.remove(index);
    }
}