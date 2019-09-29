package migrator.table.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.migration.ChangeCommand;
import migrator.migration.ColumnChange;
import migrator.migration.ColumnProperty;

public class Column implements Changable {
    protected ColumnProperty originalColumn;
    protected ColumnProperty changedColumn;
    protected ColumnChange change;

    public Column(ColumnProperty originalColumn, ColumnProperty changedColumn, ColumnChange columnChange) {
        this.originalColumn = originalColumn;
        this.changedColumn = changedColumn;
        this.change = columnChange;

        this.changedColumn.nameProperty().addListener((obs, ol, ne) -> {
            if (this.originalColumn.getName().equals(this.changedColumn.getName())) {
                this.change.nameProperty().set(null);
            } else {
                this.change.nameProperty().set(this.changedColumn.getName());
            }
         });
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

    public BooleanProperty enableNullProperty() {
        return this.changedColumn.nullProperty();
    }

    public Boolean isNullEnabled() {
        return this.enableNullProperty().get();
    }

    public Boolean isNullEnabledOriginal() {
        return this.originalColumn.isNullEnabled();
    }

    public ColumnChange getChange() {
        return this.change;
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.change.getCommand();
    }
}