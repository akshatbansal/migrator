package migrator.app.domain.change.service;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.SimpleColumnChange;
import migrator.app.migration.model.SimpleColumnProperty;

public class ColumnChangeFactory {
    public ColumnChange createNotChanged(String columnName) {
        return new SimpleColumnChange(
            columnName,
            new SimpleColumnProperty(null, null, null, null),
            new ChangeCommand(ChangeCommand.NONE)
        );
    }

    public ColumnChange createWithCreateChange(ColumnProperty columnProperty) {
        return new SimpleColumnChange(
            columnProperty.getName(),
            columnProperty,
            new ChangeCommand(ChangeCommand.CREATE)
        );
    }
}