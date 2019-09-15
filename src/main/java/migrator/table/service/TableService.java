package migrator.table.service;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.table.model.Table;

public class TableService {
    protected ObservableList<Table> list;
    protected ObjectProperty<Table> selected;

    public TableService() {
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();

        this.list.addListener((Change<? extends Table> c) -> {
            this.onListChange();
        });
    }

    protected void onListChange() {
        if (this.selected.get() == null) {
            return;
        }
        if (this.list.contains(this.selected.get())) {
            return;
        }
        this.select(null);
    }

    public ObservableList<Table> getList() {
        return this.list;
    }

    public ObjectProperty<Table> getSelected() {
        return this.selected;
    }

    public void select(Table connection) {
        this.selected.set(connection);
    }

    public void remove(Table connection) {
        this.list.remove(connection);
    }

    public void add(Table connection) {
        this.list.add(connection);
    }

    public void setAll(List<Table> tables) {
        this.list.setAll(tables);
    }
}