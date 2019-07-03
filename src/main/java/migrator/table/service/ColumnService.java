package migrator.table.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.database.service.ServerConnection;
import migrator.database.service.ServerKit;
import migrator.table.model.Column;
import migrator.table.model.Table;

public class ColumnService {
    protected ObservableList<Column> list;
    protected ObjectProperty<Column> selected;
    protected ServerKit serverKit;

    public ColumnService(TableService tableService, ServerKit serverKit) {
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
        this.serverKit = serverKit;

        this.list.addListener((Change<? extends Column> c) -> {
            this.onListChange();
        });

        tableService.getSelected().addListener((ObservableValue<? extends Table> observable, Table oldValue, Table newValue) -> {
            this.onTableSelect(newValue);
        });
    }

    protected void onTableSelect(Table table) {
        if (table == null) {
            this.list.clear();
            return;
        }

        ServerConnection serverConnection = this.serverKit.createConnection(table.getDatabase());
        serverConnection.connect();

        List<Column> columns = new ArrayList<>();
        for (List<String> columnName : serverConnection.getColumns(table.getName())) {
            columns.add(new Column(columnName.get(0), columnName.get(1), columnName.get(3), columnName.get(2) == "YES" ? true : false));
        }
        this.list.setAll(columns);
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

    public void add(Column column) {
        this.list.add(column);
    }
}