package migrator.table.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import migrator.migration.ChangeCommand;
import migrator.migration.ColumnChange;

public class Column implements Changable {
    protected StringProperty name;
    protected StringProperty format;
    protected StringProperty defaultValue;
    protected BooleanProperty enableNull;
    protected ColumnChange change;

    public Column(String name, String format, String defaultValue, boolean enableNull, ColumnChange columnChange) {
        this.name = new SimpleStringProperty(name);
        this.format = new SimpleStringProperty(format);
        this.defaultValue = new SimpleStringProperty(defaultValue);
        this.enableNull = new SimpleBooleanProperty(enableNull);
        this.change = columnChange;
        System.out.println("default: " + this.defaultValue.get());
        System.out.println("default string: " + defaultValue);

        this.change.nameProperty().addListener((obs, ol, ne) -> {
            this.onChange();
        });
        this.change.formaProperty().addListener((obs, ol, ne) -> {
            this.onChange();
        });
        this.change.defaultValueProperty().addListener((obs, ol, ne) -> {
            this.onChange();
        });
        this.change.nullProperty().addListener((obs, ol, ne) -> {
            this.onChange();
        });
    }

    public StringProperty nameProperty() {
        if (this.change != null) {
            return this.change.nameProperty();
        }
        return this.name;
    }

    public String getName() {
        return this.nameProperty().get();
    }

    public String getOriginalName() {
        return this.name.get();
    }

    public StringProperty formatProperty() {
        if (this.change != null) {
            return this.change.formaProperty();
        }
        return this.format;
    }

    public String getFormat() {
        return this.formatProperty().get();
    }

    public String getOriginalFormat() {
        return this.format.get();
    }

    public StringProperty defaultValueProperty() {
        if (this.change != null) {
            return this.change.defaultValueProperty();
        }
        return this.defaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValueProperty().get();
    }

    public String getOriginalDefaultValue() {
        return this.defaultValue.get();
    }

    public BooleanProperty enableNullProperty() {
        if (this.change != null) {
            return this.change.nullProperty();
        }
        return this.enableNull;
    }

    public Boolean isNullEnabled() {
        return this.enableNullProperty().get();
    }

    public Boolean isNullEnabledOriginal() {
        return this.enableNull.get();
    }

    public ColumnChange getChange() {
        return this.change;
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.change.getCommand();
    }

    protected Boolean originalEqualsChange() {
        System.out.println(this.getOriginalDefaultValue());
        System.out.println(this.change.defaultValueProperty().get());
        return this.change.nameProperty().get().equals(this.getOriginalName()) &&
            this.change.formaProperty().get().equals(this.getOriginalFormat()) &&
            this.change.defaultValueProperty().get().equals(this.getOriginalDefaultValue()) &&
            this.change.nullProperty().get() == this.isNullEnabledOriginal();
    }

    public void onChange() {
        if (this.originalEqualsChange() && this.change.getCommand().isType(ChangeCommand.UPDATE)) {
            this.change.getCommand().setType(null);
        } else if (!this.originalEqualsChange() && this.change.getCommand().isType(null)) {
            this.change.getCommand().setType(ChangeCommand.UPDATE);
        }
    }
}