package migrator.table.service;

import java.util.Collection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.migration.ChangeService;
import migrator.migration.TableChange;
import migrator.table.model.Column;
import migrator.table.model.Table;

public class ColumnService {
    protected ChangeService changeService;
    protected ObjectProperty<Table> activeTable;
    protected ColumnFactory columnFactory;
    protected ObservableList<Column> list;
    protected ObjectProperty<Column> selected;

    public ColumnService(ColumnFactory columnFactory, ChangeService changeService, ObjectProperty<Table> activeTable) {
        this.columnFactory = columnFactory;
        this.changeService = changeService;
        this.activeTable = activeTable;
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();

        this.list.addListener((Change<? extends Column> c) -> {
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

    public ObservableList<Column> getList() {
        return this.list;
    }

    public ObjectProperty<Column> getSelected() {
        return this.selected;
    }

    public void select(Column column) {
        this.selected.set(column);
    }

    public void remove(Column column) {
        this.list.remove(column);
    }

    // TODO: remove by table, to remove columnChange from table change

    public void add(Column column) {
        this.list.add(column);
    }

    public void setAll(Collection<Column> columns) {
        this.list.setAll(columns);
    }

    public ColumnFactory getFactory() {
        return this.columnFactory;
    }

    protected void register(Column column, Table table) {
        this.add(column);
        String dbName = table.getDatabase().getConnection().getName() + "." + table.getDatabase().getDatabase();
        TableChange tableChange = this.changeService.getTableChange(dbName, table.getOriginalName());
        tableChange.getColumnsChanges().add(column.getChange());
    }

    public void register(Column column) {
        this.register(column, this.activeTable.get());
    }
}