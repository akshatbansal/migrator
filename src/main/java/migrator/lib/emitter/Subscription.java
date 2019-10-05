package migrator.lib.emitter;

public class Subscription {
    protected Emitter emitter;
    protected String eventName;
    protected Subscriber subscriber;

    public Subscription(Emitter emitter, String eventName, Subscriber subscriber) {
        this.emitter = emitter;
        this.eventName = eventName;
        this.subscriber = subscriber;
    }

    public void unsubscribe() {
        this.emitter.off(this.eventName, this.subscriber);
    }
}