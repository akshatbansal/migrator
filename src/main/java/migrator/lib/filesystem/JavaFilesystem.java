package migrator.lib.filesystem;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaFilesystem implements Filesystem {
    @Override
    public Boolean exists(Path path) {
        return this.exists(path.toFile());
    }

    @Override
    public Boolean exists(String filePath) {
        return this.exists(new File(filePath));
    }

    @Override
    public Boolean exists(File file) {
        return file.exists();
    }

    @Override
    public String read(Path path) throws Exception {
        return new String(Files.readAllBytes(path));
    }

    @Override
    public String read(String filePath) throws Exception {
        return this.read(new File(filePath));
    }

    @Override
    public String read(File file) throws Exception {
        return this.read(file.toPath());
    }

    @Override
    public void write(Path path, String content) throws Exception {
        this.write(path.toFile(), content);
    }

    @Override
    public void write(String filePath, String content) throws Exception {
        this.write(new File(filePath), content);
    }

    @Override
    public void write(File file, String content) throws Exception {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }
}