package migrator.lib.emitter;

public interface Emitter {
    public Subscription on(String eventName, Subscriber subscriber);
    public void off(String eventName, Subscriber subscriber);
    public void emit(String eventName, Object object);
    public void emit(String eventName);
}