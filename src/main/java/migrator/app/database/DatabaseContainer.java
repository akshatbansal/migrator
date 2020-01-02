package migrator.app.database;

import java.util.Hashtable;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;

public class DatabaseContainer {
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;
    protected Map<String, DatabaseStructureFactory> factories;
    protected ObservableList<String> drivers;

    public DatabaseContainer() {
        this.applicationColumnFormatCollection = new ApplicationColumnFormatCollection();
        this.factories = new Hashtable<>();
        this.drivers = FXCollections.observableArrayList();
    }

    public ApplicationColumnFormatCollection getApplicationColumnFormatCollection() {
        return this.applicationColumnFormatCollection;
    }

    public void addStrucutreFactory(String databaseDriver, DatabaseStructureFactory factory) {
        this.factories.put(databaseDriver, factory);
        this.drivers.addAll(databaseDriver);
    }

    public DatabaseStructureFactory getStructureFactoryFor(String databaseDriver) {
        return this.factories.get(databaseDriver);
    }

    public ObservableList<String> getDrivers() {
        return this.drivers;
    }
}