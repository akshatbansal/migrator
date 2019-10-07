package migrator.lib.emitter;

public class Subscription<T> {
    protected Emitter<T> emitter;
    protected String eventName;
    protected Subscriber<T> subscriber;

    public Subscription(Emitter<T> emitter, String eventName, Subscriber<T> subscriber) {
        this.emitter = emitter;
        this.eventName = eventName;
        this.subscriber = subscriber;
    }

    public void unsubscribe() {
        this.emitter.off(this.eventName, this.subscriber);
    }
}