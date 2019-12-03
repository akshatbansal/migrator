package migrator.ext.phinx.mock;

import java.io.File;

import migrator.app.migration.FileStorageFactory;
import migrator.lib.storage.Storage;

public class FakeFileStorageFactory implements FileStorageFactory {
    protected Storage<String> storage;

    public FakeFileStorageFactory(Storage<String> storage) {
        this.storage = storage;
    }

    @Override
    public Storage<String> create(File file) {
        return this.storage;
    }
}