package migrator.app.gui.service.route;

import java.util.Hashtable;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import migrator.app.gui.view.View;

public class SimpleRouteStore implements RouteStore {
    private Map<String, View> views;
    private ObjectProperty<View> activeView;

    public SimpleRouteStore() {
        this.views = new Hashtable<>();
        this.activeView = new SimpleObjectProperty<>();
    }

    @Override
    public void addRoute(String routeName, View view) {
        this.views.put(routeName, view);
    }

    @Override
    public ObjectProperty<View> getActive() {
        return this.activeView;
    }

    @Override
    public void activate(String name) {
        if (!this.views.containsKey(name)) {
            System.out.println("Missing route'" + name + "'");
            return;
        }
        this.activeView.set(
            this.views.get(name)
        );
    }
}