package migrator.ext.flyway;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import migrator.app.migration.FileStorageFactory;
import migrator.lib.filesystem.Filesystem;
import migrator.lib.storage.SimpleFileStorage;
import migrator.lib.storage.Storage;
import migrator.lib.stringformatter.StringFormatter;

public class DateVersionFileStorageFactory implements FileStorageFactory {
    protected StringFormatter fileNameFormatter;
    private Filesystem filesystem;

    public DateVersionFileStorageFactory(Filesystem filesystem, StringFormatter fileNameFormatter) {
        this.filesystem = filesystem;
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
            .resolveSibling(dateVersion + "__" + fileName)
            .toFile();
        return new SimpleFileStorage(this.filesystem, file);
    }

    private String getCurrentVersion(File file) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM");
        final String dateVersion = "V" + dateFormat.format(new Date());
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