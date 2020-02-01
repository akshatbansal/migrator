package migrator.app;

import java.io.File;
import java.nio.file.Path;

import migrator.lib.filesystem.Filesystem;

public class ProxyFilesystem implements Filesystem {
    private Filesystem proxy;

    public ProxyFilesystem(Filesystem filesystem) {
        this.proxy = filesystem;
    }

    public void setProxy(Filesystem filesystem) {
        this.proxy = filesystem;
    }

    @Override
    public Boolean exists(File file) {
        return this.proxy.exists(file);
    }

    @Override
    public Boolean exists(Path path) {
        return this.proxy.exists(path);
    }

    @Override
    public Boolean exists(String filePath) {
        return this.proxy.exists(filePath);
    }

    @Override
    public String read(File file) throws Exception {
        return this.proxy.read(file);
    }

    @Override
    public String read(Path path) throws Exception {
        return this.proxy.read(path);
    }

    @Override
    public String read(String filePath) throws Exception {
        return this.proxy.read(filePath);
    }

    @Override
    public void write(File file, String content) throws Exception {
        this.proxy.write(file, content);
    }

    @Override
    public void write(Path path, String content) throws Exception {
        this.proxy.write(path, content);
    }

    @Override
    public void write(String filePath, String content) throws Exception {
        this.proxy.write(filePath, content);
    }
}