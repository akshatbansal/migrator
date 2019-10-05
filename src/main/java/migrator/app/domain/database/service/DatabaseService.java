package migrator.app.domain.database.service;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.domain.database.model.DatabaseConnection;

public class DatabaseService {
    protected ObservableList<DatabaseConnection> list;
    protected ObjectProperty<DatabaseConnection> connected;

    public DatabaseService() {
        this.list = FXCollections.observableArrayList();
        this.connected = new SimpleObjectProperty<>();;
    }

    public ObservableList<DatabaseConnection> getList() {
        return this.list;
    }

    public ObjectProperty<DatabaseConnection> getConnected() {
        return this.connected;
    }

    public void connect(DatabaseConnection connection) {
        this.connected.set(connection);
    }

    public void setAll(List<DatabaseConnection> databaseConnections) {
        this.list.setAll(databaseConnections);
    }
}