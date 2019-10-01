package migrator.table.model;

import javafx.beans.property.Property;
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

         this.changedColumn.formatProperty().addListener((obs, ol, ne) -> {
            if (this.originalColumn.getFormat().equals(this.changedColumn.getFormat())) {
                this.change.formatProperty().set(null);
            } else {
                this.change.formatProperty().set(this.changedColumn.getFormat());
            }
         });

         this.changedColumn.defaultValueProperty().addListener((obs, ol, ne) -> {
            if (this.originalColumn.getDefaultValue().equals(this.changedColumn.getDefaultValue())) {
                this.change.defaultValueProperty().set(null);
            } else {
                this.change.defaultValueProperty().set(this.changedColumn.getDefaultValue());
            }
         });

         this.changedColumn.nullProperty().addListener((obs, ol, ne) -> {
            if (this.originalColumn.isNullEnabled().equals(this.changedColumn.isNullEnabled())) {
                this.change.nullProperty().setValue(null);
            } else {
                this.change.nullProperty().setValue(this.changedColumn.isNullEnabled());
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
        return this.change;
    }

    public StringProperty changeTypeProperty() {
        return this.change.getCommand().typeProperty();
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.change.getCommand();
    }

    public void delete() {
        this.getChangeCommand().setType(ChangeCommand.DELETE);
    }

    public void restore() {
        this.changedColumn.nameProperty().set(this.originalColumn.getName());
        this.changedColumn.formatProperty().set(this.originalColumn.getFormat());
        this.changedColumn.defaultValueProperty().set(this.originalColumn.getDefaultValue());
        this.changedColumn.nullProperty().setValue(this.originalColumn.isNullEnabled());
        this.change.clear();
    }
}