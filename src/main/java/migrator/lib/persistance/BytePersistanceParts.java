package migrator.lib.persistance;

import java.util.Iterator;
import java.util.prefs.Preferences;

public class BytePersistanceParts implements Iterable<BytePersistance> {
    protected Preferences preferences;
    protected String key;

    public BytePersistanceParts(Preferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
    }

    @Override
    public Iterator<BytePersistance> iterator() {
        return new BytePartsIterator(this.preferences, this.key);
    }
    
}