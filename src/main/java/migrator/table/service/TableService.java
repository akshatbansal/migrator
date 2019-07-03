package migrator.table.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.database.model.DatabaseConnection;
import migrator.database.service.DatabaseService;
import migrator.database.service.ServerConnection;
import migrator.database.service.ServerKit;
import migrator.table.model.Table;

public class TableService {
    protected ObservableList<Table> list;
    protected ObjectProperty<Table> selected;
    protected ServerKit serverKit;

    public TableService(DatabaseService databaseService, ServerKit serverKit) {
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
        this.serverKit = serverKit;

        this.list.addListener((Change<? extends Table> c) -> {
            this.onListChange();
        });

        databaseService.getConnected().addListener((ObservableValue<? extends DatabaseConnection> observable, DatabaseConnection oldValue, DatabaseConnection newValue) -> {
            this.onDatabaseConnect(newValue);
        });
    }

    protected void onDatabaseConnect(DatabaseConnection connection) {
        if (connection == null) {
            this.list.clear();
            return;
        }

        ServerConnection serverConnection = this.serverKit.createConnection(connection);
        serverConnection.connect();

        List<Table> tables = new ArrayList<>();
        for (String tableNames : serverConnection.getTables()) {
            tables.add(new Table(connection, tableNames));
        }
        this.list.setAll(tables);
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
}