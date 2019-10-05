package migrator.app.router;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ActiveRoute {
    protected ObjectProperty<Route> route;

    public ActiveRoute() {
        this.route = new SimpleObjectProperty<>();
    }

    public void changeTo(String name, Object value) {
        this.route.set(
            new Route(name, value)
        );
    }

    public void changeTo(String name) {
        this.changeTo(name, null);
    }

    public ObjectProperty<Route> routeProperty() {
        return this.route;
    }
}