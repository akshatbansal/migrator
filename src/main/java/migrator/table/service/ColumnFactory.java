package migrator.table.service;

import migrator.migration.ChangeCommand;
import migrator.migration.ColumnProperty;
import migrator.migration.SimpleColumnChange;
import migrator.migration.SimpleColumnProperty;
import migrator.table.model.Column;

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
            new SimpleColumnProperty(columnName, "string", "", false), // original
            new SimpleColumnProperty(columnName, "string", "", false), // changed
            new SimpleColumnChange(columnName, this.simpleColumnProperty(null, null, null, null), new ChangeCommand(ChangeCommand.CREATE))
        );
    }
}