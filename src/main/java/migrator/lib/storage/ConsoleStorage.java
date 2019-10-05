package migrator.lib.storage;

public class ConsoleStorage implements Storage {
    @Override
    public void store(String data) {
        System.out.println(data);
    }
}