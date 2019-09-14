package migrator.phpphinx.mock;

import migrator.common.Storage;

public class FileStorage implements Storage {
    protected String data;

    public void store(String data) {
        this.data = data;
    }

    public String load() {
        return this.data;
    }
}