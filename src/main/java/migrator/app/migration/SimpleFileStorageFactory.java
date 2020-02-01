package migrator.app.migration;

import java.io.File;

import migrator.lib.filesystem.Filesystem;
import migrator.lib.storage.SimpleFileStorage;
import migrator.lib.storage.Storage;

public class SimpleFileStorageFactory implements FileStorageFactory {
    private Filesystem filesystem;

    public SimpleFileStorageFactory(Filesystem filesystem) {
        this.filesystem = filesystem;
    }

    @Override
    public Storage<String> create(File file) {
        return new SimpleFileStorage(
            this.filesystem,
            file
        );
    }
}