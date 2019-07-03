package migrator.connection.service;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.connection.model.Connection;

public class ConnectionService {
    protected ObservableList<Connection> list;
    protected ObjectProperty<Connection> selected;
    protected ObjectProperty<Connection> connected;

    public ConnectionService() {
        Connection test = new Connection("local");
        test.setDriver("mysql");
        test.setPort("3306");
        test.setHost("localhost");
        this.list = FXCollections.observableArrayList(test);
        this.selected = new SimpleObjectProperty<>();
        this.connected = new SimpleObjectProperty<>();

        this.list.addListener((Change<? extends Connection> c) -> {
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

    public ObservableList<Connection> getList() {
        return this.list;
    }

    public ObjectProperty<Connection> getSelected() {
        return this.selected;
    }

    public ObjectProperty<Connection> getConnected() {
        return this.connected;
    }

    public void select(Connection connection) {
        this.selected.set(connection);
    }

    public void connect(Connection connection) {
        this.connected.set(connection);
    }

    public void remove(Connection connection) {
        this.list.remove(connection);
    }

    public void add(Connection connection) {
        this.list.add(connection);
    }

    public void clear() {
        this.list.clear();
    }
}