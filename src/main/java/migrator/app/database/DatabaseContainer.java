package migrator.app.database;

public interface DatabaseContainer {
    public DatabaseStructureFactory getStructureFactoryFor(String databaseDriver);
    public void addStrucutreFactory(String databaseDriver, DatabaseStructureFactory factory);
}