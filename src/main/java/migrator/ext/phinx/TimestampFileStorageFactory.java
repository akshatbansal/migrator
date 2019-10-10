package migrator.ext.phinx;

import java.io.File;

import migrator.lib.storage.Storage;
import migrator.lib.stringformatter.StringFormatter;

public class TimestampFileStorageFactory {
    protected StringFormatter fileNameFormatter;

    public TimestampFileStorageFactory(StringFormatter fileNameFormatter) {
        this.fileNameFormatter = fileNameFormatter;
    }

    public Storage create(File file) {
        return new TimestampFileStorage(file, fileNameFormatter);
    }
}