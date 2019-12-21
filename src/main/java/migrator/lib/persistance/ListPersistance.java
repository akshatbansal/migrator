package migrator.lib.persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class ListPersistance<T> implements Persistance<List<T>>  {
    protected Preferences preferences;
    protected String key;

    public ListPersistance(String key) {
        this.preferences = Preferences.userRoot();
        this.key = key;
    }

    @Override
    public boolean exists() {
        BytePersistance firstIndexFirstPart = new BytePersistance(this.preferences, this.key + ".0-pt0");
        return firstIndexFirstPart.exists();
    }

    @Override
    public List<T> load() {
        return this.load(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> load(List<T> def) {
        List<T> list = new ArrayList<>();
        boolean has = false;
        Iterable<Persistance<Object>> parts = new PersistanceParts(this.getPreferances(), this.key);
        for (Persistance<Object> part : parts) {
            Object o = part.load();
            if (o == null) {
                continue;
            }
            has = true;
            list.add((T) o);
        }
        if (!has) {
            return def;
        }
        return list;
    }

    @Override
    public void clear() {
        Iterable<Persistance<Object>> parts = new PersistanceParts(this.getPreferances(), this.key);
        for (Persistance<Object> part : parts) {
            part.clear();
        }        
    }

    @Override
    public void store(List<T> list) {
        this.clear();
        int i = 0;
        for (T item : list) {
            ObjectPersistance itemPersistance = new ObjectPersistance(this.key + "." + i);
            itemPersistance.store(item);
            i++;
        }
    }

    public Preferences getPreferances() {
        return this.preferences;
    }
}