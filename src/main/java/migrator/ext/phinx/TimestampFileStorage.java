package migrator.ext.phinx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import migrator.lib.storage.Storage;
import migrator.lib.stringformatter.StringFormatter;

public class TimestampFileStorage implements Storage {
    protected File file;
    protected SimpleDateFormat dateFormat;
    protected StringFormatter fileNameFormatter;

    public TimestampFileStorage(File file, StringFormatter fileNameFormatter) {
        this.fileNameFormatter = fileNameFormatter;
        this.dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = this.dateFormat.format(new Date());

        String fileName = this.fileNameFormatter.format(
            file.getName()
        );
        if (!fileName.endsWith(".php")) {
            fileName += ".php";
        }

        this.file = file.toPath()
            .resolveSibling(timestamp + "_" + fileName)
            .toFile();
    }

    public void store(String data) {
        try {
            FileWriter fileWriter = new FileWriter(this.file);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}