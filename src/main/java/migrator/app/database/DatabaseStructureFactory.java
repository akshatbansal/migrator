package migrator.app.database;

public interface DatabaseStructureFactory {
    public DatabaseStructure create(String url, String user, String password);
}