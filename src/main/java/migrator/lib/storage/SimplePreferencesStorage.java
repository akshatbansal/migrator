package migrator.lib.storage;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class SimplePreferencesStorage implements Storage<String> {
    protected Preferences preferences;
    protected String key;

    public SimplePreferencesStorage(Preferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
    }

    @Override
    public void clear() {
        try {
            this.preferences.clear();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String load() {
        return this.preferences.get(this.key, "");
    }

    @Override
    public void store(String data) {
        this.preferences.put(this.key, data);
    }


}