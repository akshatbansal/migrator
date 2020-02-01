package migrator.lib.storage;

import java.io.File;

import migrator.lib.filesystem.Filesystem;

public class SimpleFileStorage implements Storage<String> {
    protected File file;
    private Filesystem filesystem;

    public SimpleFileStorage(Filesystem filesystem, File file) {
        this.file = file;
        this.filesystem = filesystem;
        if (!this.filesystem.exists(file)) {
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
            contents = this.filesystem.read(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }

    @Override
    public void store(String data) {
        try {
            this.filesystem.write(this.file, data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}