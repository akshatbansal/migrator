package migrator.lib.dispatcher;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventDispatcher {
    protected Map<String, List<EventHandler>> handlers;

    public EventDispatcher() {
        this.handlers = new Hashtable<>();
    }

    public void dispatch(Event<?> event) {
        if (!this.handlers.containsKey(event.getName())) {
            System.out.println("No handler for event '" + event.getName() + "'");
            return;
        }
        for (EventHandler handler : this.handlers.get(event.getName())) {
            handler.handle(event);
        }
    }

    public void register(String eventName, EventHandler handler) {
        if (!this.handlers.containsKey(eventName)) {
            this.handlers.put(eventName, new LinkedList<>());
        }
        this.handlers.get(eventName).add(handler);
    }

    public void unregister(String eventName, EventHandler handler) {
        if (!this.handlers.containsKey(eventName)) {
            this.handlers.put(eventName, new LinkedList<>());
        }
        this.handlers.get(eventName).remove(handler);
    }
}