package migrator.app.domain.table.model;

import javafx.beans.property.StringProperty;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.migration.model.TableChange;
import migrator.app.migration.model.TableProperty;

public class Table {
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

    public String getOriginalName() {
        return this.originalTable.getName();
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

    public void restore() {
        this.changedTable.nameProperty().set(this.originalTable.getName());
        this.change.clear();
    }
}