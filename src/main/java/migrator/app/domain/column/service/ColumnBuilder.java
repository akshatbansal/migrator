package migrator.app.domain.column.service;

import migrator.app.database.format.ColumnFormatManager;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.SimpleColumnProperty;

public class ColumnBuilder {
    protected String id;
    protected String tableId;
    protected ColumnProperty original;
    protected ColumnProperty change;
    protected ChangeCommand changeCommand;
    protected ColumnFormatManager columnFormatManager;

    public ColumnBuilder(ColumnFormatManager columnFormatManager) {
        this.columnFormatManager = columnFormatManager;
        this.id = "1";
        this.tableId = "1";
        this.original = new SimpleColumnProperty("1", "", "", "", false, "", false, "", false);
        this.change = new SimpleColumnProperty("1", "", "", "", false, "", false, "", false);
        this.changeCommand = new ChangeCommand("1", "");
    }

    public ColumnBuilder withId(String id) {
        this.id = id;
        return this;
    }
    
    public ColumnBuilder withTableId(String tableId) {
        this.tableId = tableId;
        return this;
    }

    public ColumnBuilder withChange(String change) {
        this.changeCommand.setType(change);
        return this;
    }

    public ColumnBuilder withOriginalName(String name) {
        this.original.nameProperty().set(name);
        return this;
    }

    public ColumnBuilder withChangeName(String name) {
        this.change.nameProperty().set(name);
        return this;
    }

    public ColumnBuilder withOriginalFormat(String format) {
        this.original.formatProperty().set(format);
        return this;
    }

    public ColumnBuilder withChangeFormat(String format) {
        this.change.formatProperty().set(format);
        return this;
    }

    public ColumnBuilder withChangeNull() {
        this.change.nullProperty().setValue(true);
        return this;
    }

    public ColumnBuilder withOriginalSign() {
        this.original.signProperty().setValue(true);
        return this;
    }

    public ColumnBuilder withChangeSign() {
        this.change.signProperty().setValue(true);
        return this;
    }

    public ColumnBuilder withChangeAutoIncrement() {
        this.change.autoIncrementProperty().setValue(true);
        return this;
    }

    public ColumnBuilder withOriginalDefaultValue(String value) {
        this.original.defaultValueProperty().set(value);;
        return this;
    }

    public ColumnBuilder withChangeDefaultValue(String value) {
        this.change.defaultValueProperty().set(value);;
        return this;
    }

    public ColumnBuilder withOriginalLength(String length) {
        this.original.lengthProperty().set(length);
        return this;
    }

    public ColumnBuilder withChangeLength(String value) {
        this.change.lengthProperty().set(value);;
        return this;
    }

    public ColumnBuilder withOriginalPrecision(String value) {
        this.original.precisionProperty().set(value);;
        return this;
    }

    public ColumnBuilder withChangePrecision(String value) {
        this.change.precisionProperty().set(value);;
        return this;
    }

    public Column build() {
        return new Column(
            this.columnFormatManager,
            this.id,
            this.tableId,
            this.original,
            this.change,
            this.changeCommand
        );
    }
}