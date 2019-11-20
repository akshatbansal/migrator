package migrator.lib.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class SimpleFileStorage implements Storage<String> {
    protected File file;

    public SimpleFileStorage(File file) {
        this.file = file;
        if (!Files.exists(file.toPath())) {
            this.clear();
        }
    }

    @Override
    public void clear() {
        this.store("");
    }

    @Override
    public String load() {
        String contents = null;
        try {
            contents = new String(Files.readAllBytes(this.file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    @Override
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