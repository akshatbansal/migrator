package migrator.lib.config;

import java.util.HashMap;
import java.util.Map;

public class MapConfig<T> {
    protected Map<String, T> values;

    public MapConfig() {
        this.values = new HashMap<>();
    }

    public void add(String name, T value) {
        if (this.values.containsKey(name)) {

        }
        this.values.put(name, value);
    }

    public Map<String, T> getValues() {
        return this.values;
    }
}