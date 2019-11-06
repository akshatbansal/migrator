package migrator.app.domain.database.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.domain.connection.model.Connection;

public class DatabaseConnection implements Serializable {
    private static final long serialVersionUID = 8048352979891263214L;
    protected transient StringProperty database;
    protected Connection connection;
    protected transient StringProperty url;

    public DatabaseConnection(Connection connection, String database) {
        this.connection = connection;
        this.database = new SimpleStringProperty(database);
        this.url = new SimpleStringProperty();
        this.initialize();
    }

    protected void initialize() {
        this.url.bind(
            Bindings.concat(this.connection.urlProperty(), "/", this.database)
        );
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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(this.database.get());

        s.writeUTF(this.url.get());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.database = new SimpleStringProperty(s.readUTF());

        this.url = new SimpleStringProperty(s.readUTF());
        this.initialize();
    }
}