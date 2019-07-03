package migrator.persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class ListPersistance<T> implements Persistance<List<T>>  {
    protected ObjectPersistance objectPersistance;

    public ListPersistance() {
        this.objectPersistance = new ObjectPersistance();
    }

    public boolean has(String name) {
        return this.objectPersistance.has(name);
    }

    public List<T> load(String name) {
        return this.load(name, null);
    }

    public List<T> load(String name, List<T> def) {
        List<T> list = new ArrayList<>();
        int i = 0;
        boolean has = false;
        while (true) {
            has = true;
            String key = name + "." + i;
            if (!this.has(key)) {
                break;
            }
            i++;
            Object o = this.objectPersistance.load(key);
            if (o == null) {
                continue;
            }
            list.add((T) o);
        }
        if (!has) {
            return def;
        }
        return list;
    }

    public void clear(String name) {
        Integer i = 0;
        while (true) {
            String key = name + "." + i.toString();
            if (!this.has(key)) {
                return;
            }
            this.objectPersistance.clear(key);
            i++;
        }
    }

    public void store(String name, List<T> list) {
        this.clear(name);
        int i = 0;
        for (T s : list) {
            this.objectPersistance.store(name + "." + i, s);
            i++;
        }
    }

    public Preferences getPreferances() {
        return this.objectPersistance.getPreferances();
    }
}