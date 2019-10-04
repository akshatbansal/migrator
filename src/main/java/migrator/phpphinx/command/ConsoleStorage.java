package migrator.phpphinx.command;

import migrator.common.Storage;

public class ConsoleStorage implements Storage {
    @Override
    public void store(String data) {
        System.out.println(data);
    }
}