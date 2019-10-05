package migrator.app.domain.table.service;

import migrator.app.domain.table.model.Column;
import migrator.migration.ChangeCommand;
import migrator.migration.ColumnProperty;
import migrator.migration.SimpleColumnChange;
import migrator.migration.SimpleColumnProperty;

public class ColumnFactory {
    protected ColumnProperty simpleColumnProperty(String name, String format, String defaultValue, Boolean enableNull) {
        return new SimpleColumnProperty(name, format, defaultValue, enableNull);
    }

    public Column createNotChanged(String columnName, String format, String defaultValue, Boolean enableNull) {
        return new Column(
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull), // original
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull), // changed
            new SimpleColumnChange(columnName, this.simpleColumnProperty(null, null, null, null), new ChangeCommand(ChangeCommand.NONE))
        );
    }

    public Column createWithCreateChange(String columnName) {
        return new Column(
            new SimpleColumnProperty(columnName, null, null, null), // original
            new SimpleColumnProperty(columnName, "string", "", false), // changed
            new SimpleColumnChange(columnName, this.simpleColumnProperty(columnName, "string", "", false), new ChangeCommand(ChangeCommand.CREATE))
        );
    }
}