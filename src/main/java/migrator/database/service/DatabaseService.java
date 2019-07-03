package migrator.database.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.connection.model.Connection;
import migrator.connection.service.ConnectionService;
import migrator.database.model.DatabaseConnection;

public class DatabaseService {
    protected ObservableList<DatabaseConnection> list;
    protected ObjectProperty<DatabaseConnection> connected;
    protected ServerKit serverKit;

    public DatabaseService(ConnectionService connectionService, ServerKit serverKit) {
        this.list = FXCollections.observableArrayList();
        this.serverKit = serverKit;
        this.connected = new SimpleObjectProperty<>();

        connectionService.getConnected().addListener((ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) -> {
            this.onConnectConnection(newValue);
        });
        this.onConnectConnection(connectionService.getConnected().get());
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

    protected void onConnectConnection(Connection connection) {
        if (connection == null) {
            this.list.clear();
            return;
        }
        ServerConnection serverConnection = this.serverKit.createConnection(connection);
        serverConnection.connect();

        List<DatabaseConnection> databases = new ArrayList<>();
        for (String databaseName : serverConnection.getDatabases()) {
            databases.add(new DatabaseConnection(connection, databaseName));
        }
        this.getList().setAll(databases);
    }
}