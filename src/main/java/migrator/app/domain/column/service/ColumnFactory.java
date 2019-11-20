package migrator.app.domain.column.service;

import migrator.app.database.format.ColumnFormatManager;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.lib.uid.Generator;

public class ColumnFactory {
    protected ColumnFormatManager columnFormatManager;
    protected Generator idGenerator;

    public ColumnFactory(ColumnFormatManager columnFormatManager, Generator idGenerator) {
        this.columnFormatManager = columnFormatManager;
        this.idGenerator = idGenerator;
    }

    public Column createWithCreateChange(String tableId, String columnName) {
        return this.createWithCreateChange(tableId, columnName, "string", "", false, "255", true, "", false);
    }

    public Column createWithCreateChange(String tableId, String columnName, String format, String defaultValue, Boolean nullEnabled, String length, Boolean signed, String precision, Boolean autoIncrement) {
        return new Column(
            this.columnFormatManager,
            this.idGenerator.next(),
            tableId,
            new SimpleColumnProperty(this.idGenerator.next(), columnName, format, defaultValue, nullEnabled, length, signed, precision, autoIncrement), // original
            new SimpleColumnProperty(this.idGenerator.next(), columnName, format, defaultValue, nullEnabled, length, signed, precision, autoIncrement), // changed
            new ChangeCommand(this.idGenerator.next(), ChangeCommand.CREATE)
        );
    }

    public Column createNotChanged(String tableId, String columnName, String format, String defaultValue, Boolean enableNull, String length, Boolean sign, String precision, Boolean autoIncrement) {
        return new Column(
            this.columnFormatManager,
            this.idGenerator.next(),
            tableId,
            new SimpleColumnProperty(this.idGenerator.next(), columnName, format, defaultValue, enableNull, length, sign, precision, autoIncrement), // original
            new SimpleColumnProperty(this.idGenerator.next(), columnName, format, defaultValue, enableNull, length, sign, precision, autoIncrement), // changed
            new ChangeCommand(this.idGenerator.next(), ChangeCommand.NONE)
        );
    }
}