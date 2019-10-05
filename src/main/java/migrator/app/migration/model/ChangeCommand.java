package migrator.app.migration.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChangeCommand {
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String NONE = null;

    protected StringProperty name;

    public ChangeCommand() {
        this(null);
    }

    public ChangeCommand(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public Boolean isType(String name) {
        return this.name.get() == name;
    }

    public StringProperty typeProperty() {
        return this.name;
    }

    public String getType() {
        return this.name.get();
    }

    public void setType(String type) {
        this.name.set(type);
    }
}