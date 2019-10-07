package migrator.lib.config;

import java.util.Map;

public class MapConfig<T> {
    protected Map<String, T> values;


    public void add(String name, T value) {
        this.values.put(name, value);
    }

    public Map<String, T> getValues() {
        return this.values;
    }
}