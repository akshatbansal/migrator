package migrator.table.model;

import java.io.Serializable;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import migrator.database.model.DatabaseConnection;
import migrator.migration.TableChange;
import migrator.migration.TableProperty;

public class Table implements Serializable {
    protected TableProperty originalTable;
    protected TableProperty changedTable;
    protected DatabaseConnection database;
    protected TableChange change;

    public Table(DatabaseConnection database, TableProperty originalTable, TableProperty changedProperty, TableChange tableChange) {
        this.originalTable = originalTable;
        this.changedTable = changedProperty;
        this.database = database;
        this.change = tableChange;

        this.changedTable.nameProperty().addListener(
            new ChangeStringPropertyListener(
                this.originalTable.nameProperty(),
                this.change.nameProperty()
            )
        );
    }

    public String getName() {
        return this.changedTable.getName();
    }
    public void setName(String value) {
        this.changedTable.nameProperty().set(value);
    }
    public StringProperty nameProperty() {
        return this.changedTable.nameProperty();
    }

    public DatabaseConnection getDatabase() {
        return this.database;
    }

    public String toString() {
        return this.getName();
    }

    public TableChange getChange() {
        return this.change;
    }
}