package migrator.app.domain.change.service;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleTableChange;
import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableChange;

public class TableChangeFactory {
    public TableChange createNotChanged(String tableName) {
        return new SimpleTableChange(
            tableName,
            new SimpleTableProperty(null),
            new ChangeCommand(ChangeCommand.NONE)
        );
    }

    public TableChange createWithCreateChange(String tableName) {
        return new SimpleTableChange(
            tableName,
            new SimpleTableProperty(tableName),
            new ChangeCommand(ChangeCommand.CREATE)
        );
    }
}