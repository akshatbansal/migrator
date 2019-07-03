package migrator.persistance;

import java.util.Iterator;
import java.util.prefs.Preferences;

public class PersistanceParts implements Iterable<String> {
    protected Preferences preferences;
    protected String key;

    public PersistanceParts(Preferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
    }

    @Override
    public Iterator<String> iterator() {
        return new PartsIterator(this.preferences, this.key);
    }
    
}