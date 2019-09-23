package migrator.migration;

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
        return (String) this.command.getArgument("format");
    }

    public ChangeCommand getCommand() {
        return this.command;
    }
}