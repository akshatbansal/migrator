package migrator.lib.persistance;

import java.util.prefs.Preferences;

public interface Persistance<T>  {
    public boolean exists();
    public T load(T def);
    public T load();
    public void store(T value);
    public void clear();
    public Preferences getPreferances();
}