package migrator.lib.persistance;

import java.util.prefs.Preferences;

public class BytePersistance {
    Preferences userPreferences;
    protected String key;

    public BytePersistance(Preferences preferences, String key) {
        this.userPreferences = preferences;
        this.key = key;
    }

    public boolean exists() {
        return this.load().length > 0;
    }

    public byte[] load() {
        byte[] emptyArray = {};
        return this.userPreferences.getByteArray(this.key, emptyArray);
    }

    public void clear() {
        this.userPreferences.remove(this.key);
    }
}