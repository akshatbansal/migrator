package migrator.app.database;

public interface DatabaseConnectDriver<T> {
    public ConnectionResult<T> connect();
}