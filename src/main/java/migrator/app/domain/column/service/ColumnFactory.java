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

    public Column createWithCreateChange(String columnName) {
        return new Column(
            this.columnFormatManager,
            new SimpleColumnProperty(columnName, "string", "", false, "255", true, ""), // original
            new SimpleColumnProperty(columnName, "string", "", false, "255", true, ""), // changed
            new ChangeCommand(ChangeCommand.CREATE)
        );
    }

    public Column createNotChanged(String columnName, String format, String defaultValue, Boolean enableNull, String length, Boolean sign, String precision) {
        return new Column(
            this.columnFormatManager,
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull, length, sign, precision), // original
            new SimpleColumnProperty(columnName, format, defaultValue, enableNull, length, sign, precision), // changed
            new ChangeCommand(ChangeCommand.NONE)
        );
    }
}