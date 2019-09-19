package migrator.migration;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChangeCommand {
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    protected StringProperty name;
    protected Map<String, Object> arguments;

    public ChangeCommand() {
        this(null, new HashMap<>());
    }

    public ChangeCommand(String name) {
        this(name, new HashMap<>());
    }

    public ChangeCommand(String name, Map<String, Object> arguments) {
        this.name = new SimpleStringProperty(name);
        this.arguments = arguments;

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

    public Map<String, Object> getArguments() {
        return this.arguments;
    }

    public Object getArgument(String name) {
        return this.arguments.get(name);
    }

    public Boolean hasArgument(String name) {
        return this.getArgument(name) != null;
    }
}