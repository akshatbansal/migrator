package migrator.app.domain.table.service;

import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.table.model.Table;
import migrator.migration.SimpleTableProperty;
import migrator.migration.TableChange;
import migrator.migration.TableChangeFactory;
import migrator.migration.TableProperty;

public class TableFactory {
    protected TableChangeFactory tableChangeFactory;

    public TableFactory(TableChangeFactory tableChangeFactory) {
        this.tableChangeFactory = tableChangeFactory;
    }

    public Table createNotChanged(DatabaseConnection connection, String tableName) {
        return new Table(
            connection,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            this.tableChangeFactory.createNotChanged(tableName)
        );
    }

    public Table createWithCreateChange(DatabaseConnection connection, String tableName) {
        return new Table(
            connection,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            this.tableChangeFactory.createWithCreateChange(tableName)
        );
    }

    public Table create(DatabaseConnection connection, String tableName, TableChange change) {
        TableProperty tableProperty = new SimpleTableProperty(tableName);
        if (change.getName() != null) {
            tableProperty.nameProperty().set(change.getName());
        }
        return new Table(
            connection,
            new SimpleTableProperty(tableName),
            tableProperty,
            change
        );
    }
}