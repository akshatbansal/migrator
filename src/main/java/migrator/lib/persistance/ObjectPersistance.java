package migrator.lib.persistance;

import java.util.prefs.Preferences;

import migrator.lib.persistance.helpers.ArraysUtil;
import migrator.lib.persistance.helpers.Serialize;

public class ObjectPersistance implements Persistance<Object>  {
    Preferences userPreferences;
    protected String key;

    public ObjectPersistance(String key) {
        this.userPreferences = Preferences.userRoot();
        this.key = key;
    }

    @Override
    public boolean exists() {
        return this.load(this.key) != null;
    }

    @Override
    public Object load() {
        return this.load(null);
    }

    @Override
    public Object load(Object def) {
        byte[] byteArray = this.loadByteArray(this.key);
        if (byteArray.length == 0) {
            return def;
        }
        return Serialize.deserialize(byteArray);
    }

    protected byte[] loadByteArray(String name) {
        byte[] result = {};
        Iterable<BytePersistance> parts = new BytePersistanceParts(this.getPreferances(), name);
        for (BytePersistance part : parts) {
            result = ArraysUtil.join(result, part.load());
        }
        return result;
    }

    @Override
    public void clear() {
        Iterable<BytePersistance> parts = new BytePersistanceParts(this.getPreferances(), this.key);
        for (BytePersistance part : parts) {
            part.clear();
        }
    }

    @Override
    public void store(Object object) {
        byte[] data = Serialize.serialize(object);
        byte[][] chunks = ArraysUtil.chunk(data, Preferences.MAX_VALUE_LENGTH / 4 * 3);
        for (int i = 0; i < chunks.length; i++) {
            this.userPreferences.putByteArray(this.key + "-pt" + i, chunks[i]);
        }
    }

    public Preferences getPreferances() {
        return this.userPreferences;
    }
}