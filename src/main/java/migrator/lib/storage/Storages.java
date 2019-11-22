package migrator.lib.storage;

import java.io.File;
import java.util.prefs.Preferences;

import migrator.lib.adapter.Adapter;

public class Storages {
    public static <T> Storage<T> getFileStorage(File file, Adapter<T, String> adapter) {
        return new PersistantStorage<>(
            new SimpleFileStorage(file),
            adapter
        );
    }

    public static Storage<String> getSimpleFileStorage(File file) {
        return Storages.getFileStorage(file, new StringAdapter());
    }

    public static <T> Storage<T> getPreferencesStorage(Preferences preferences, String key, Adapter<T, String> adapter) {
        return new PersistantStorage<>(
            new SimplePreferencesStorage(preferences, key),
            adapter
        );
    }

    public static Storage<String> getSimplePreferencesStorage(Preferences preferences, String key) {
        return Storages.getPreferencesStorage(
            preferences,
            key,
            new StringAdapter()
        );
    }
}