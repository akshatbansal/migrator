package migrator.lib.storage;

public interface Storage<T> {
    public void store(T item);
    public void clear();
    public T load();
}