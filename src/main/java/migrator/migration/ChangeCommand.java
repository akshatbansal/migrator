package migrator.migration;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChangeCommand {
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String NONE = null;

    protected StringProperty name;
    protected Map<String, Observable> arguments;

    public ChangeCommand() {
        this(null, new HashMap<>());
    }

    public ChangeCommand(String name) {
        this(name, new HashMap<>());
    }

    public ChangeCommand(String name, Map<String, Observable> arguments) {
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

    public Map<String, Observable> getArguments() {
        return this.arguments;
    }

    public Object getArgument(String name) {
        return this.argumentProperty(name).get();
    }

    public String getStringArgument(String name) {
        return this.argumentPropertyString(name).get();
    }

    public Boolean getBooleanArgument(String name) {
        return this.argumentPropertyBoolean(name).get();
    }

    public ObjectProperty<Object> argumentProperty(String name) {
        if (!this.arguments.containsKey(name)) {
            this.arguments.put(name, new SimpleObjectProperty<>());
        }
        return (ObjectProperty<Object>) this.arguments.get(name);
    }

    public StringProperty argumentPropertyString(String name) {
        if (!this.arguments.containsKey(name)) {
            this.arguments.put(name, new SimpleStringProperty());
        }
        return (StringProperty) this.arguments.get(name);
    }

    public BooleanProperty argumentPropertyBoolean(String name) {
        if (!this.arguments.containsKey(name)) {
            this.arguments.put(name, new SimpleBooleanProperty());
        }
        return (BooleanProperty) this.arguments.get(name);
    }

    public Boolean hasArgument(String name) {
        return this.arguments.containsKey(name) && this.getArgument(name) != null;
    }
}