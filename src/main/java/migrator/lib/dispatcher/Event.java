package migrator.lib.dispatcher;

public interface Event<T> {
    public T getValue();
    public String getName();
}