package migrator.migration;

import java.util.HashMap;
import java.util.Map;

public class ChangeCommand {
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    protected String name;
    protected Map<String, Object> arguments;

    public ChangeCommand() {
        this(null, new HashMap<>());
    }

    public ChangeCommand(String name) {
        this(name, new HashMap<>());
    }

    public ChangeCommand(String name, Map<String, Object> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public Boolean isType(String name) {
        return this.name == name;
    }

    public String getType() {
        return this.name;
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