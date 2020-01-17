package migrator.app.gui.service.route;

import javafx.beans.property.ObjectProperty;
import migrator.app.gui.route.RouteView;

public interface RouteStore {
    public ObjectProperty<RouteView> getActive();
    public void activate(String name);
    public void addRoute(String routeName, RouteView view);
}