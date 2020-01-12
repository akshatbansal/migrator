package migrator.app.gui.component.breadcrump;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class EventBreadcrump implements Breadcrump {
    private StringProperty nameProperty;
    private String eventName;
    private EventDispatcher dispatcher;

    public EventBreadcrump(StringProperty name, EventDispatcher dispatcher, String eventName) {
        this.nameProperty = name;
        this.dispatcher = dispatcher;
        this.eventName = eventName;
    }

    public EventBreadcrump(String name, EventDispatcher dispatcher, String eventName) {
        this(new SimpleStringProperty(name), dispatcher, eventName);
    }

    @Override
    public StringProperty nameProperty() {
        return this.nameProperty;
    }

    @Override
    public String getName() {
        return this.nameProperty.get();
    }

    @Override
    public void link() {
        this.dispatcher.dispatch(
            new SimpleEvent<>(this.eventName)
        );
    }
}