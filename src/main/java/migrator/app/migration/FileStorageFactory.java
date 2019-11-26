package migrator.app.migration;

import java.io.File;

import migrator.lib.storage.Storage;

public interface FileStorageFactory {
    public Storage<String> create(File file);
}