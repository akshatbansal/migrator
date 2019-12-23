package migrator.app.database;

import java.util.Map;

public class MapDatabaseContainer implements DatabaseContainer {
    protected Map<String, DatabaseStructureFactory> factories;

    @Override
    public void addStrucutreFactory(String databaseDriver, DatabaseStructureFactory factory) {
        this.factories.put(databaseDriver, factory);
    }

    @Override
    public DatabaseStructureFactory getStructureFactoryFor(String databaseDriver) {
        return this.factories.get(databaseDriver);
    }
}