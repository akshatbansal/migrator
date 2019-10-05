package migrator.app.migration.model;

import java.util.Arrays;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

public class SimpleColumnChange implements ColumnChange {
    protected String columnName;
    protected ColumnProperty columnProperty;
    protected ChangeCommand command;

    public SimpleColumnChange(String columnName, ColumnProperty columnProperty, ChangeCommand command) {
        this.columnName = columnName;
        this.columnProperty = columnProperty;
        this.command = command;

        UpdateCommandListener updateCommandListener = new UpdateCommandListener(
            this.command,
            Arrays.asList(
                this.columnProperty.nameProperty(),
                this.columnProperty.formatProperty(),
                this.columnProperty.defaultValueProperty(),
                this.columnProperty.nullProperty()
            )
        );
        this.columnProperty.nameProperty().addListener(updateCommandListener);
        this.columnProperty.formatProperty().addListener(updateCommandListener);
        this.columnProperty.defaultValueProperty().addListener(updateCommandListener);
        this.columnProperty.nullProperty().addListener(updateCommandListener);
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
    public Property<Boolean> nullProperty() {
        return this.columnProperty.nullProperty();
    }

    @Override
    public ChangeCommand getCommand() {
        return this.command;
    }

    @Override
    public void clear() {
        this.columnProperty.nameProperty().set(null);
        this.columnProperty.formatProperty().set(null);
        this.columnProperty.defaultValueProperty().set(null);
        this.columnProperty.nullProperty().setValue(null);
        this.command.setType(ChangeCommand.NONE);
    }

    public Boolean isAnyPropertyChanged() {
        return this.getName() != null ||
            this.getFormat() != null ||
            this.getDefaultValue() != null ||
            this.isNullEnabled() != null;
    }

    public void evalCommadType() {
        if (this.command.isType(ChangeCommand.DELETE)) {
            return;
        }
        if (this.command.isType(ChangeCommand.CREATE)) {
            return;
        }
        if (this.isAnyPropertyChanged()) {
            this.command.setType(ChangeCommand.UPDATE);
        } else if (!this.isAnyPropertyChanged()) {
            this.command.setType(ChangeCommand.NONE);
        }
    }
}