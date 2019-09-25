package migrator.migration;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ColumnChange {
    protected ChangeCommand command;
    protected StringProperty name;

    public ColumnChange(String name, ChangeCommand command) {
        this.name = new SimpleStringProperty(name);
        this.command = command;
    }

    public String getName() {
        return this.name.get();
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getFormat() {
        return this.formaProperty().get();
    }

    public StringProperty formaProperty() {
        return  this.command.argumentPropertyString("format");
    }

    public String getDefaultValue() {
        return this.defaultValueProperty().get();
    }

    public StringProperty defaultValueProperty() {
        return this.command.argumentPropertyString("default");
    }

    public Boolean isNullEnabled() {
        return this.nullProperty().get();
    }

    public BooleanProperty nullProperty() {
        return this.command.argumentPropertyBoolean("null");
    }

    public ChangeCommand getCommand() {
        return this.command;
    }
}