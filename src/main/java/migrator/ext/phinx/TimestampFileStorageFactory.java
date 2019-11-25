package migrator.ext.phinx;

import java.io.File;

import migrator.app.migration.FileStorageFactory;
import migrator.lib.storage.Storage;
import migrator.lib.stringformatter.StringFormatter;

public class TimestampFileStorageFactory implements FileStorageFactory {
    protected StringFormatter fileNameFormatter;

    public TimestampFileStorageFactory(StringFormatter fileNameFormatter) {
        this.fileNameFormatter = fileNameFormatter;
    }

    public Storage<String> create(File file) {
        return new TimestampFileStorage(file, fileNameFormatter);
    }
}