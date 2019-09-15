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
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();
        this.connected = new SimpleObjectProperty<>();

        this.list.addListener((Change<? extends Connection> c) -> {
            this.onListChange();
        });
    }

    public ConnectionService(Connection ... connections) {
        this();
        this.list.addAll(connections);
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

    public void deselect() {
        this.select(null);
    }

    public void connect(Connection connection) {
        this.connected.set(connection);
    }

    public void disconnect() {
        this.connect(null);
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