package migrator.app.domain.column.service;

import migrator.app.database.format.ColumnFormatManager;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleColumnProperty;

public class ColumnFactory {
    protected ColumnFormatManager columnFormatManager;

    public ColumnFactory(ColumnFormatManager columnFormatManager) {
        this.columnFormatManager = columnFormatManager;
    }

    public Column createNotChanged(String columnName, String format, String defaultValue, Boolean enableNull, String length) {
        return new Column(
            this.columnFormatManager,
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull, length, true, ""), // original
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull, length, true, ""), // changed
            new ChangeCommand(ChangeCommand.NONE)
        );
    }

    public Column createWithCreateChange(String columnName) {
        return new Column(
            this.columnFormatManager,
            new SimpleColumnProperty(columnName, "string", "", false, "255", true, ""), // original
            new SimpleColumnProperty(columnName, "string", "", false, "255", true, ""), // changed
            new ChangeCommand(ChangeCommand.CREATE)
        );
    }

    public Column createIntegerNotChanged(String columnName, String format, String defaultValue, Boolean enableNull, String length, Boolean sign) {
        return new Column(
            this.columnFormatManager,
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull, length, sign, ""), // original
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull, length, sign, ""), // changed
            new ChangeCommand(ChangeCommand.NONE)
        );
    }

    public Column createDecimalNotChanged(String columnName, String format, String defaultValue, Boolean enableNull, String length, Boolean sign, String precision) {
        return new Column(
            this.columnFormatManager,
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull, length, sign, precision), // original
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull, length, sign, precision), // changed
            new ChangeCommand(ChangeCommand.NONE)
        );
    }
}