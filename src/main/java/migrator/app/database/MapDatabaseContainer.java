package migrator.app.database;

import java.util.Hashtable;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MapDatabaseContainer implements DatabaseContainer {
    protected Map<String, DatabaseStructureFactory> factories;
    protected ObservableList<String> drivers;

    public MapDatabaseContainer() {
        this.factories = new Hashtable<>();
        this.drivers = FXCollections.observableArrayList();
    }

    @Override
    public void addStrucutreFactory(String databaseDriver, DatabaseStructureFactory factory) {
        this.factories.put(databaseDriver, factory);
        this.drivers.addAll(databaseDriver);
    }

    @Override
    public DatabaseStructureFactory getStructureFactoryFor(String databaseDriver) {
        return this.factories.get(databaseDriver);
    }

    @Override
    public ObservableList<String> getDrivers() {
        return this.drivers;
    }
}