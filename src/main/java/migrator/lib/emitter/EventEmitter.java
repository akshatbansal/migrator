package migrator.lib.emitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventEmitter implements Emitter {
    protected Map<String, List<Subscriber>> events;

    public EventEmitter() {
        this.events = new HashMap<>();
    }

    @Override
    public Subscription on(String eventName, Subscriber subscriber) {
        if (!this.events.containsKey(eventName)) {
            this.events.put(eventName, new ArrayList<>());
        }
        this.events.get(eventName).add(subscriber);
        return new Subscription(this, eventName, subscriber);
    }

    @Override
    public void off(String eventName, Subscriber subscriber) {
        if (!this.events.containsKey(eventName)) {
            return;
        }
        this.events.get(eventName).remove(subscriber);
    }

    @Override
    public void emit(String eventName, Object object) {
        if (!this.events.containsKey(eventName)) {
            return;
        }
        for (Subscriber subscriber : this.events.get(eventName)) {
            subscriber.next(object);
        }
    }

    @Override
    public void emit(String eventName) {
        this.emit(eventName, null);
    }
}