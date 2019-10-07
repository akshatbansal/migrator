package migrator.lib.emitter;

public interface Emitter<T> {
    public Subscription<T> on(String eventName, Subscriber<T> subscriber);
    public void off(String eventName, Subscriber<T> subscriber);
    public void emit(String eventName, T object);
    public void emit(String eventName);
}