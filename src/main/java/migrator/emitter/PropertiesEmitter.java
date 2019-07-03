package migrator.emitter;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class PropertiesEmitter implements Emitter {
    protected Emitter emitter;

    public PropertiesEmitter(Observable ...observables) {
        this.emitter = new EventEmitter();

        for (Observable o : observables) {
            o.addListener(new InvalidationListener(){
                @Override
                public void invalidated(Observable observable) {
                    emitter.emit("change", observable);
                }
            });
        }
    }

    @Override
    public void emit(String eventName) {
        this.emit(eventName, null);   
    }

    @Override
    public void emit(String eventName, Object object) {
        this.emitter.emit("change", object);
    }

    @Override
    public void off(String eventName, Subscriber subscriber) {
        this.emitter.off(eventName, subscriber);
    }

    @Override
    public Subscription on(String eventName, Subscriber subscriber) {
        return this.emitter.on(eventName, subscriber);
    }
}