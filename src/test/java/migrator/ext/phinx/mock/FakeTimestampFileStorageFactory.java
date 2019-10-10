package migrator.ext.phinx.mock;

import java.io.File;

import migrator.ext.phinx.TimestampFileStorageFactory;
import migrator.lib.storage.Storage;
import migrator.lib.stringformatter.UnderscoreFormatter;

public class FakeTimestampFileStorageFactory extends TimestampFileStorageFactory {
    protected Storage storage;

    public FakeTimestampFileStorageFactory(Storage storage) {
        super(new UnderscoreFormatter());
        this.storage = storage;
    }

    @Override
    public Storage create(File file) {
        return this.storage;
    }
}