package migrator.app.database;

public class ConnectionResult<T> {
    protected boolean ok;
    protected String error;
    protected T connection;

    public ConnectionResult(T connection) {
        this.ok = true;
        this.connection = connection;
        this.error = "";
    }

    public ConnectionResult(String error) {
        this.ok = false;
        this.connection = null;
        this.error = error;
    }

    public boolean isOk() {
        return this.ok;
    }

    public String getError() {
        return this.error;
    }

    public T getConnection() {
        return this.connection;
    }
}