package migrator.persistance;

import java.util.Iterator;
import java.util.prefs.Preferences;

public class PartsIterator implements Iterator<String> {
    protected Preferences preferences;
    protected String key;
    protected Integer index;

    public PartsIterator(Preferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
        this.index = 0;
    }

    protected String getKey(Integer index) {
        return this.key + "-pt" + index.toString();
    }

    protected boolean hasPart(String key) {
        byte[] emptyArray = {};
        byte[] partialResult = this.preferences.getByteArray(key, emptyArray);
        return partialResult.length > 0;
    }

    @Override
    public boolean hasNext() {
        return this.hasPart(this.getKey(this.index));
    }

    @Override
    public String next() {
        Integer currentIndex = this.index;
        this.index++;
        return this.getKey(currentIndex);
    }

    
}