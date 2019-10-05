package migrator.app.database.driver;

public interface DatabaseDriverFactory {
    public DatabaseDriver create(String url, String user, String password);
}