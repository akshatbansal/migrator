package migrator.app.gui.component.breadcrump;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.gui.route.RouteChangeEvent;
import migrator.lib.dispatcher.EventDispatcher;

public class RouteBreadcrump implements Breadcrump {
    private StringProperty nameProperty;
    private String routeName;
    private EventDispatcher dispatcher;

    public RouteBreadcrump(StringProperty name, EventDispatcher dispatcher, String routeName) {
        this.nameProperty = name;
        this.dispatcher = dispatcher;
        this.routeName = routeName;
    }

    public RouteBreadcrump(String name, EventDispatcher dispatcher, String routeName) {
        this(new SimpleStringProperty(name), dispatcher, routeName);
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
            new RouteChangeEvent(this.routeName)
        );
    }
}