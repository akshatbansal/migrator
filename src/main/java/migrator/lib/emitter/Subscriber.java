package migrator.lib.emitter;

public interface Subscriber<T> {
    public void next(T object);
}