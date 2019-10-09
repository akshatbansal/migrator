package migrator.app.domain.table.model;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.ColumnProperty;

public class Column implements Changable, ColumnChange, ChangeListener<Object> {
    protected ColumnProperty originalColumn;
    protected ColumnProperty changedColumn;
    protected ChangeCommand changeCommand;

    public Column(ColumnProperty originalColumn, ColumnProperty changedColumn, ChangeCommand changeCommand) {
        this.originalColumn = originalColumn;
        this.changedColumn = changedColumn;
        this.changeCommand = changeCommand;

        this.changedColumn.nameProperty().addListener(this);
        this.changedColumn.formatProperty().addListener(this);
        this.changedColumn.defaultValueProperty().addListener(this);
        this.changedColumn.nullProperty().addListener(this);
    }

    public StringProperty nameProperty() {
        return this.changedColumn.nameProperty();
    }

    public String getName() {
        return this.nameProperty().get();
    }

    public String getOriginalName() {
        return this.originalColumn.getName();
    }

    public StringProperty formatProperty() {
        return this.changedColumn.formatProperty();
    }

    public String getFormat() {
        return this.formatProperty().get();
    }

    public String getOriginalFormat() {
        return this.originalColumn.getFormat();
    }

    public StringProperty defaultValueProperty() {
        return this.changedColumn.defaultValueProperty();
    }

    public String getDefaultValue() {
        return this.defaultValueProperty().get();
    }

    public String getOriginalDefaultValue() {
        return this.originalColumn.getDefaultValue();
    }

    public Property<Boolean> enableNullProperty() {
        return this.changedColumn.nullProperty();
    }

    public Boolean isNullEnabled() {
        return this.enableNullProperty().getValue();
    }

    public Boolean isNullEnabledOriginal() {
        return this.originalColumn.isNullEnabled();
    }

    public ColumnChange getChange() {
        return this;
    }

    public StringProperty changeTypeProperty() {
        return this.changeCommand.typeProperty();
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.changeCommand;
    }

    public void delete() {
        this.getChangeCommand().setType(ChangeCommand.DELETE);
    }

    @Override
    public void restore() {
        this.changedColumn.nameProperty().set(this.originalColumn.getName());
        this.changedColumn.formatProperty().set(this.originalColumn.getFormat());
        this.changedColumn.defaultValueProperty().set(this.originalColumn.getDefaultValue());
        this.changedColumn.nullProperty().setValue(this.originalColumn.isNullEnabled());
        this.changeCommand.setType(ChangeCommand.NONE);
    }

    @Override
    public ChangeCommand getCommand() {
        return this.getChangeCommand();
    }

    @Override
    public ColumnProperty getOriginal() {
        return this.originalColumn;
    }

    @Override
    public Boolean hasDefaultValueChanged() {
        return !this.getDefaultValue().equals(this.getOriginal().getDefaultValue());
    }

    @Override
    public Boolean hasFormatChanged() {
        return !this.getFormat().equals(this.getOriginal().getFormat());
    }

    @Override
    public Boolean hasNameChanged() {
        return !this.getName().equals(this.getOriginal().getName());
    }

    @Override
    public Boolean hasNullEnabledChanged() {
        return this.isNullEnabled() != this.getOriginal().isNullEnabled();
    }

    @Override
    public Property<Boolean> nullProperty() {
        return this.enableNullProperty();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (this.changeCommand.isType(ChangeCommand.DELETE)) {
            return;
        }
        if (this.changeCommand.isType(ChangeCommand.CREATE)) {
            return;
        }
        
        if (this.hasNameChanged() || this.hasFormatChanged() || this.hasDefaultValueChanged() || this.hasNullEnabledChanged()) {
            this.changeCommand.typeProperty().set(ChangeCommand.UPDATE);
            return;
        }
        this.changeCommand.typeProperty().set(ChangeCommand.NONE);
    }
}