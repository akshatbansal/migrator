package migrator.app.domain;

import java.util.Hashtable;
import java.util.Map;

import migrator.lib.persistantsystem.Persistantsystem;

public class MockPersistantsystem implements Persistantsystem {
    private Map<String, Object> data;

    public MockPersistantsystem() {
        this.data = new Hashtable<>();
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        if (!this.data.containsKey(key)) {
            return def;
        }
        return (boolean) this.data.get(key);
    }

    @Override
    public double getDouble(String key, double def) {
        if (!this.data.containsKey(key)) {
            return def;
        }
        return (double) this.data.get(key);
    }

    @Override
    public String getString(String key, String def) {
        if (!this.data.containsKey(key)) {
            return def;
        }
        return (String) this.data.get(key);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        this.data.put(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        this.data.put(key, value);
    }

    @Override
    public void putString(String key, String value) {
        this.data.put(key, value);
    }
}