package migrator.app.gui.service.route;

import java.util.Hashtable;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import migrator.app.gui.route.RouteView;

public class SimpleRouteStore implements RouteStore {
    private Map<String, RouteView> views;
    private ObjectProperty<RouteView> activeView;

    public SimpleRouteStore() {
        this.views = new Hashtable<>();
        this.activeView = new SimpleObjectProperty<>();
    }

    @Override
    public void addRoute(String routeName, RouteView view) {
        this.views.put(routeName, view);
    }

    @Override
    public ObjectProperty<RouteView> getActive() {
        return this.activeView;
    }

    @Override
    public void activate(String name) {
        if (!this.views.containsKey(name)) {
            System.out.println("Missing route'" + name + "'");
            return;
        }
        if (this.activeView.get() != null) {
            this.activeView.get().suspend();
        }
        this.views.get(name).activate();
        this.activeView.set(
            this.views.get(name)
        );
    }
}