package migrator.app.domain.database.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.domain.connection.model.Connection;

public class DatabaseConnection {
    protected StringProperty database;
    protected Connection connection;
    protected StringProperty url;

    public DatabaseConnection(Connection connection, String database) {
        this.connection = connection;
        this.database = new SimpleStringProperty(database);
        this.url = new SimpleStringProperty();
        this.url.bind(Bindings.concat(this.connection.getUrl(), "/", this.database));
    }

    public String getDatabase() {
        return this.database.get();
    }
    public void setDatabase(String value) {
        this.database.set(value);
    }
    public StringProperty databaseProperty() {
        return this.database;
    }

    public String getUrl() {
        return this.url.get();
    }
    public StringProperty urlProperty() {
        return this.url;
    }

    public String toString() {
        return this.getDatabase();
    }

    public Connection getConnection() {
        return this.connection;
    }
}