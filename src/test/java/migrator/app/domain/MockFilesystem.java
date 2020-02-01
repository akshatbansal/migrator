package migrator.app.domain;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

import migrator.lib.filesystem.Filesystem;

public class MockFilesystem implements Filesystem {
    private Map<String, String> files;

    @Override
    public Boolean exists(File file) {
        return this.exists(file.getAbsolutePath());
    }

    @Override
    public Boolean exists(Path path) {
        return this.exists(path.toAbsolutePath().toString());
    }

    @Override
    public Boolean exists(String filePath) {
         return this.files.containsKey(filePath);
    }

    @Override
    public String read(File file) throws Exception {
        return this.read(file.getAbsolutePath());
    }

    @Override
    public String read(Path path) throws Exception {
        return this.read(path.toAbsolutePath().toString());
    }

    @Override
    public String read(String filePath) throws Exception {
        return this.files.get(filePath);
    }

    @Override
    public void write(File file, String content) throws Exception {
        this.write(file.getAbsolutePath(), content);
    }

    @Override
    public void write(Path path, String content) throws Exception {
        this.write(path.toAbsolutePath().toString(), content);
    }

    @Override
    public void write(String filePath, String content) throws Exception {
        this.files.put(filePath, content);
    }
}