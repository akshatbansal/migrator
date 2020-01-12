package migrator.app.code;

import java.util.Hashtable;
import java.util.Map;

public class CodeContainer {
    protected Map<String, CodeCommandFactory> commandFactories;

    public CodeContainer() {
        this.commandFactories = new Hashtable<>();
    }

    public void addCommandFactory(String name, CodeCommandFactory factory) {
        this.commandFactories.put(name, factory);
    }

    public CodeCommandFactory getCommandFactory(String name) {
        return this.commandFactories.get(name);
    }
}