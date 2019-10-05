package migrator.app.code;

import java.util.HashMap;
import java.util.Map;

public class CodeConfig {
    protected Map<String, CodeCommandFactory> commandFactories;

    public CodeConfig() {
        this.commandFactories = new HashMap<>();
    }

    public void addCommandFactory(String name, CodeCommandFactory factory) {
        this.commandFactories.put(name, factory);
    }

    public Map<String, CodeCommandFactory> getCommandFactories() {
        return this.commandFactories;
    }
}