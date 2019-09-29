package migrator.migration;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

public class SimpleColumnChange implements ColumnChange {
    protected ChangeCommand command;
    protected String columnName;
    protected ColumnProperty columnProperty;

    public SimpleColumnChange(String columnName, ColumnProperty columnProperty, ChangeCommand command) {
        this.columnName = columnName;
        this.columnProperty = columnProperty;
        this.command = command;

        this.columnProperty.nameProperty().addListener((obs, ol, ne) -> {
            this.evalCommadType();
        });

        this.columnProperty.formatProperty().addListener((obs, ol, ne) -> {
            this.evalCommadType();
        });

        this.columnProperty.defaultValueProperty().addListener((obs, ol, ne) -> {
            this.evalCommadType();
        });

        this.columnProperty.nullProperty().addListener((obs, ol, ne) -> {
            this.evalCommadType();
        });
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

    protected Boolean isAnyPropertyChanged() {
        return this.getName() != null ||
            this.getFormat() != null ||
            this.getDefaultValue() != null ||
            this.isNullEnabled() != null;
    }

    public void evalCommadType() {
        if (this.command.isType(ChangeCommand.NONE) && this.isAnyPropertyChanged()) {
            this.command.setType(ChangeCommand.UPDATE);
        } else if (this.command.isType(ChangeCommand.UPDATE) && !this.isAnyPropertyChanged()) {
            this.command.setType(ChangeCommand.NONE);
        }
    }
}