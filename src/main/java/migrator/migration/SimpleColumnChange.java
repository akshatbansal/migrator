package migrator.migration;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class SimpleColumnChange implements ColumnChange {
    protected ChangeCommand command;
    protected String columnName;
    protected ColumnProperty columnProperty;

    public SimpleColumnChange(String columnName, ColumnProperty columnProperty, ChangeCommand command) {
        this.columnName = columnName;
        this.columnProperty = columnProperty;
        this.command = command;
    }

    @Override
    public String getName() {
        return this.columnProperty.getName();
    }

    @Override
    public String getOriginalName() {
        return this.columnName;
    }

    @Override
    public Boolean hasNameChanged() {
        return !this.getOriginalName().equals(this.getName());
    }

    @Override
    public StringProperty nameProperty() {
        return this.columnProperty.nameProperty();
    }

    @Override
    public String getFormat() {
        return this.columnProperty.getFormat();
    }

    @Override
    public StringProperty formatProperty() {
        return  this.columnProperty.formatProperty();
    }

    @Override
    public String getDefaultValue() {
        return this.columnProperty.getDefaultValue();
    }

    @Override
    public StringProperty defaultValueProperty() {
        return this.columnProperty.defaultValueProperty();
    }

    @Override
    public Boolean isNullEnabled() {
        return this.columnProperty.isNullEnabled();
    }

    @Override
    public BooleanProperty nullProperty() {
        return this.columnProperty.nullProperty();
    }

    @Override
    public ChangeCommand getCommand() {
        return this.command;
    }
}