package migrator.app.gui.service.route;

import javafx.beans.property.ObjectProperty;
import migrator.app.gui.view.View;

public interface RouteStore {
    public ObjectProperty<View> getActive();
    public void activate(String name);
    public void addRoute(String routeName, View view);
}