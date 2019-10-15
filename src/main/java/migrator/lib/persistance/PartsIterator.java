package migrator.lib.persistance;

import java.util.Iterator;
import java.util.prefs.Preferences;

public class PartsIterator implements Iterator<Persistance<Object>> {
    protected Preferences preferences;
    protected String key;
    protected Integer index;

    public PartsIterator(Preferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
        this.index = 0;
    }

    protected String getKey(Integer index) {
        return this.key + "." + index.toString();
    }

    protected boolean hasPart(String key) {
        byte[] emptyArray = {};
        byte[] partialResult = this.preferences.getByteArray(key + "-pt0", emptyArray);
        return partialResult.length > 0;
    }

    @Override
    public boolean hasNext() {
        return this.hasPart(this.getKey(this.index));
    }

    @Override
    public Persistance<Object> next() {
        Integer currentIndex = this.index;
        this.index++;
        return new ObjectPersistance(this.getKey(currentIndex));
    }
}