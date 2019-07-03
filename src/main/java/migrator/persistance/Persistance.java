package migrator.persistance;

import java.util.prefs.Preferences;

public interface Persistance<T>  {
    public boolean has(String name);
    public T load(String name, T def);
    public T load(String name);
    public void store(String name, T value);
    public void clear(String name);
    public Preferences getPreferances();
}