package migrator.ext.flyway;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import migrator.app.migration.FileStorageFactory;
import migrator.lib.storage.Storage;
import migrator.lib.storage.Storages;
import migrator.lib.stringformatter.StringFormatter;

public class DateVersionFileStorageFactory implements FileStorageFactory {
    protected StringFormatter fileNameFormatter;

    public DateVersionFileStorageFactory(StringFormatter fileNameFormatter) {
        this.fileNameFormatter = fileNameFormatter;
    }

    @Override
    public Storage<String> create(File file) {
        String dateVersion = this.getCurrentVersion(file);
        String fileName = this.fileNameFormatter.format(
            file.getName()
        );
        if (!fileName.endsWith(".sql")) {
            fileName += ".sql";
        }

        file = file.toPath()
            .resolveSibling(dateVersion + "_" + fileName)
            .toFile();
        return Storages.getSimpleFileStorage(file);
    }

    private String getCurrentVersion(File file) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM");
        final String dateVersion = dateFormat.format(new Date());
        File[] currentVersionFiles = file.getParentFile().listFiles((File dir, String name) -> {
            return name.startsWith(dateVersion);
        });
        int lastVersionNumber = 0;
        if (currentVersionFiles != null) {
            lastVersionNumber = currentVersionFiles.length;
        }
        return dateVersion + "." + lastVersionNumber;
    }
}