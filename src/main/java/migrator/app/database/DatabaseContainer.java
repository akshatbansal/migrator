package migrator.app.database;

import java.util.Hashtable;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.database.column.format.SimpleAppColumnFormat;

public class DatabaseContainer {
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;
    protected Map<String, DatabaseStructureFactory> factories;
    protected ObservableList<String> drivers;

    public DatabaseContainer() {
        this.applicationColumnFormatCollection = new ApplicationColumnFormatCollection();
        this.factories = new Hashtable<>();
        this.drivers = FXCollections.observableArrayList();

        ApplicationColumnFormat basicFormat = new SimpleAppColumnFormat(false, false, false, false);
        ApplicationColumnFormat lengthFormat = new SimpleAppColumnFormat(true, false, false, false);
        ApplicationColumnFormat intFormat = new SimpleAppColumnFormat(true, false, true, true);
        ApplicationColumnFormat decimalFormat = new SimpleAppColumnFormat(true, true, true, false);
        
        this.applicationColumnFormatCollection.addFormat("boolean", basicFormat);
        this.applicationColumnFormatCollection.addFormat("char", lengthFormat);
        this.applicationColumnFormatCollection.addFormat("date", basicFormat);
        this.applicationColumnFormatCollection.addFormat("datetime", basicFormat);
        this.applicationColumnFormatCollection.addFormat("decimal", decimalFormat);
        this.applicationColumnFormatCollection.addFormat("float", decimalFormat);
        this.applicationColumnFormatCollection.addFormat("integer", intFormat);
        this.applicationColumnFormatCollection.addFormat("long", intFormat);
        this.applicationColumnFormatCollection.addFormat("string", lengthFormat);
        this.applicationColumnFormatCollection.addFormat("text", basicFormat);
        this.applicationColumnFormatCollection.addFormat("time", basicFormat);
        this.applicationColumnFormatCollection.addFormat("timestamp", basicFormat);
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