package migrator.lib.storage;

public class ConsoleStorage implements Storage<String> {
    @Override
    public void store(String data) {
        System.out.println(data);
    }

    @Override
    public void clear() {}

    @Override
    public String load() {
        return "";
    }
}