package migrator.lib.persistance;

import java.util.Iterator;
import java.util.prefs.Preferences;

public class PersistanceParts implements Iterable<Persistance<Object>> {
    protected Preferences preferences;
    protected String key;

    public PersistanceParts(Preferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
    }

    @Override
    public Iterator<Persistance<Object>> iterator() {
        return new PartsIterator(this.preferences, this.key);
    }
    
}