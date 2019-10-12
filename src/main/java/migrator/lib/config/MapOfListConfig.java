package migrator.lib.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapOfListConfig<T> {
    protected Map<String, List<T>> values;

    public MapOfListConfig() {
        this.values = new HashMap<>();
    }

    public void add(String name, T value) {
        if (!this.values.containsKey(name)) {
            this.values.put(name, new ArrayList<>());
        }
        this.values.get(name).add(value);
    }

    public void add(String name, List<T> values) {
        if (!this.values.containsKey(name)) {
            this.values.put(name, new ArrayList<>());
        }
        for (T item : values) {
            this.values.get(name).add(item);
        }
    }

    public Map<String, List<T>> getValues() {
        return this.values;
    }

    public List<T> getList(String name) {
        return this.values.get(name);
    }
}