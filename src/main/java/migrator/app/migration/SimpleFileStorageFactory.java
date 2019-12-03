package migrator.app.migration;

import java.io.File;

import migrator.lib.storage.Storage;
import migrator.lib.storage.Storages;

public class SimpleFileStorageFactory implements FileStorageFactory {
    @Override
    public Storage<String> create(File file) {
        return Storages.getSimpleFileStorage(file);
    }
}