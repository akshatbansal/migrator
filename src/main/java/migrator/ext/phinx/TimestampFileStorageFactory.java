package migrator.ext.phinx;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import migrator.app.migration.FileStorageFactory;
import migrator.lib.storage.Storage;
import migrator.lib.storage.Storages;
import migrator.lib.stringformatter.StringFormatter;

public class TimestampFileStorageFactory implements FileStorageFactory {
    protected StringFormatter fileNameFormatter;

    public TimestampFileStorageFactory(StringFormatter fileNameFormatter) {
        this.fileNameFormatter = fileNameFormatter;
    }

    public Storage<String> create(File file) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        String fileName = this.fileNameFormatter.format(
            file.getName()
        );
        if (!fileName.endsWith(".php")) {
            fileName += ".php";
        }

        file = file.toPath()
            .resolveSibling(timestamp + "_" + fileName)
            .toFile();

        return Storages.getSimpleFileStorage(file);
    }
}