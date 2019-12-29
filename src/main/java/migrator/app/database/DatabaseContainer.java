package migrator.app.database;

import javafx.collections.ObservableList;

public interface DatabaseContainer {
    public DatabaseStructureFactory getStructureFactoryFor(String databaseDriver);
    public void addStrucutreFactory(String databaseDriver, DatabaseStructureFactory factory);
    public ObservableList<String> getDrivers();
}