package migrator.lib.persistantsystem;

import java.util.prefs.Preferences;

public class JavaPersistantsystem implements Persistantsystem {
    @Override
    public String getString(String key, String def) {
        return Preferences.userRoot().get(key, def);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return Preferences.userRoot().getBoolean(key, def);
    }

    @Override
    public double getDouble(String key, double def) {
        return Preferences.userRoot().getDouble(key, def);
    }

    @Override
    public void putString(String key, String value) {
        Preferences.userRoot().put(key, value);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        Preferences.userRoot().putBoolean(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        Preferences.userRoot().putDouble(key, value);
    }
}