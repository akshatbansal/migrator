package migrator.app.domain.column.service;

import migrator.app.domain.table.model.Column;
import migrator.app.gui.column.format.ColumnFormat;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.lib.uid.Generator;

public class ColumnFactory {
    protected Generator idGenerator;
    protected ColumnFormatCollection columnFormatCollection;

    public ColumnFactory(Generator idGenerator, ColumnFormatCollection columnFormatCollection) {
        this.idGenerator = idGenerator;
        this.columnFormatCollection = columnFormatCollection;
    }

    public Column createWithCreateChange(String tableId, String columnName) {
        return this.createWithCreateChange(tableId, columnName, "string", "", false, "255", true, "", false);
    }

    public Column createWithCreateChange(String tableId, String columnName, String format, String defaultValue, Boolean nullEnabled, String length, Boolean signed, String precision, Boolean autoIncrement) {
        Column column = new Column(
            this.idGenerator.next(),
            tableId,
            new SimpleColumnProperty(this.idGenerator.next(), columnName, format, defaultValue, nullEnabled, length, signed, precision, autoIncrement), // original
            new SimpleColumnProperty(this.idGenerator.next(), columnName, format, defaultValue, nullEnabled, length, signed, precision, autoIncrement), // changed
            new ChangeCommand(this.idGenerator.next(), ChangeCommand.CREATE)
        );
        ColumnFormat columnFormat = this.columnFormatCollection.getFormatByName(column.formatProperty().get());
        columnFormat.updateModel(column);

        return column;
    }

    public Column createNotChanged(String tableId, String columnName, String format, String defaultValue, Boolean enableNull, String length, Boolean sign, String precision, Boolean autoIncrement) {
        Column column = new Column(
            this.idGenerator.next(),
            tableId,
            new SimpleColumnProperty(this.idGenerator.next(), columnName, format, defaultValue, enableNull, length, sign, precision, autoIncrement), // original
            new SimpleColumnProperty(this.idGenerator.next(), columnName, format, defaultValue, enableNull, length, sign, precision, autoIncrement), // changed
            new ChangeCommand(this.idGenerator.next(), ChangeCommand.NONE)
        );
        ColumnFormat columnFormat = this.columnFormatCollection.getFormatByName(column.formatProperty().get());
        columnFormat.updateModel(column);
        return column;
    }
}