package migrator.app.domain.table.service;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.app.domain.table.model.Table;
import migrator.migration.ChangeService;

public class TableService {
    protected TableFactory tableFactory;
    protected ChangeService changeService;
    protected ObservableList<Table> list;
    protected ObjectProperty<Table> selected;

    public TableService(ChangeService changeService, TableFactory tableFactory) {
        this.tableFactory = tableFactory;
        this.changeService = changeService;
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

    public void register(Table table) {
        this.list.add(table);
        String dbName = table.getDatabase().getConnection().getName() + "." + table.getDatabase().getDatabase();
        this.changeService.addTableChange(dbName, table.getChange());
    }

    public void setAll(List<Table> tables) {
        this.list.setAll(tables);
    }

    public TableFactory getFactory() {
        return this.tableFactory;
    }
}