package migrator.phpphinx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import migrator.common.Storage;

public class TimestampFileStorage implements Storage {
    protected String folder;
    protected String name;

    public TimestampFileStorage(String folder, String name) {
        this.folder = folder;
        this.name = name;
    }

    public void store(String data) {
        File file = new File(this.folder + "/111_" + this.name);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}