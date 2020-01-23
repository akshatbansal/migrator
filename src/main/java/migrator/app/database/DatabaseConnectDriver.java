package migrator.app.database;

public interface DatabaseConnectDriver<T> extends AutoCloseable {
    public ConnectionResult<T> connect();
} 