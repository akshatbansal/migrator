package migrator.ext.phinx.mock;

import java.io.File;

import migrator.ext.phinx.TimestampFileStorageFactory;
import migrator.lib.storage.Storage;

public class FakeTimestampFileStorageFactory extends TimestampFileStorageFactory {
    protected Storage storage;

    public FakeTimestampFileStorageFactory(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Storage create(File file) {
        return this.storage;
    }
}