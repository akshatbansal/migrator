package migrator.app.domain.table.service;

import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.SimpleColumnChange;
import migrator.app.migration.model.SimpleColumnProperty;

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

    public Column create(String name, String format, String defaultValue, Boolean enableNull, ColumnChange change) {
        ColumnProperty columnProperty = new SimpleColumnProperty(name, format, defaultValue, enableNull);
        if (change.getName() != null) {
            columnProperty.nameProperty().set(change.getName());
        }
        if (change.getFormat() != null) {
            columnProperty.formatProperty().set(change.getFormat());
        }
        if (change.getDefaultValue() != null) {
            columnProperty.defaultValueProperty().set(change.getDefaultValue());
        }
        if (change.isNullEnabled() != null) {
            columnProperty.nullProperty().setValue(change.isNullEnabled());
        }

        return new Column(
            new SimpleColumnProperty(name, format, defaultValue, enableNull), // original
            columnProperty, // changed
            change
        );
    }
}