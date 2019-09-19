package migrator.table.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.database.model.DatabaseConnection;
import migrator.migration.TableChange;

public class Table implements Serializable {
    protected StringProperty name;
    protected DatabaseConnection database;
    protected TableChange change;

    public Table(DatabaseConnection database, String name) {
        this(database, name, null);
    }

    public Table(DatabaseConnection database, String name, TableChange tableChange) {
        this.name = new SimpleStringProperty(name);
        this.database = database;
        this.change = tableChange;
    }

    public String getName() {
        return this.name.get();
    }
    public void setName(String value) {
        this.name.set(value);
    }
    public StringProperty nameProperty() {
        return this.name;
    }

    public DatabaseConnection getDatabase() {
        return this.database;
    }

    public String toString() {
        return this.getName();
    }
}