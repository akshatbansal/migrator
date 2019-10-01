package migrator.table.service;

import java.util.ArrayList;

import migrator.database.model.DatabaseConnection;
import migrator.migration.ChangeCommand;
import migrator.migration.SimpleTableChange;
import migrator.migration.SimpleTableProperty;
import migrator.table.model.Table;

public class TableFactory {
    public Table createNotChanged(DatabaseConnection connection, String tableName) {
        return new Table(
            connection,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            new SimpleTableChange(
                tableName,
                new SimpleTableProperty(null),
                new ChangeCommand(ChangeCommand.NONE),
                new ArrayList<>(),
                new ArrayList<>()
            )
        );
    }

    public Table createWithCreateChange(DatabaseConnection connection, String tableName) {
        return new Table(
            connection,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            new SimpleTableChange(
                tableName,
                new SimpleTableProperty(tableName),
                new ChangeCommand(ChangeCommand.CREATE),
                new ArrayList<>(),
                new ArrayList<>()
            )
        );
    }
}