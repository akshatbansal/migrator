package migrator.persistance;

import java.util.prefs.Preferences;

import migrator.common.ArraysUtil;
import migrator.common.Serialize;;

public class ObjectPersistance implements Persistance<Object>  {
    Preferences userPreferences;

    public ObjectPersistance() {
        this.userPreferences = Preferences.userRoot();
    }

    public boolean has(String name) {
        return this.load(name) != null;
    }

    public Object load(String name) {
        return this.load(name, null);
    }

    public Object load(String name, Object def) {
        byte[] byteArray = this.loadByteArray(name);
        if (byteArray.length == 0) {
            return def;
        }
        return Serialize.deserialize(byteArray);
    }

    protected byte[] loadByteArray(String name) {
        byte[] emptyArray = {};
        byte[] result = {};
        Iterable<String> partKeys = new PersistanceParts(this.getPreferances(), name);
        for (String key : partKeys) {
            byte[] partialResult = this.userPreferences.getByteArray(key, emptyArray);
            result = ArraysUtil.join(result, partialResult);
        }
        return result;
    }

    public void clear(String name) {
        Iterable<String> partKeys = new PersistanceParts(this.getPreferances(), name);
        for (String key : partKeys) {
            this.getPreferances().remove(key);
        }
    }

    public void store(String name, Object object) {
        byte[] data = Serialize.serialize(object);
        byte[][] chunks = ArraysUtil.chunk(data, Preferences.MAX_VALUE_LENGTH / 4 * 3);
        for (int i = 0; i < chunks.length; i++) {
            this.userPreferences.putByteArray(name + "-pt" + i, chunks[i]);
        }
    }

    public Preferences getPreferances() {
        return this.userPreferences;
    }
}