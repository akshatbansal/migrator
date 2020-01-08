package migrator.app.gui.breadcrumps;

import javafx.beans.property.StringProperty;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.router.ActiveRoute;

public class RouteBreadcrump implements Breadcrump {
    protected StringProperty name;
    protected ActiveRoute activeRoute;
    protected String route;

    public RouteBreadcrump(StringProperty name, ActiveRoute activeRoute, String route) {
        this.name = name;
        this.activeRoute = activeRoute;
        this.route = route;
    }

    @Override
    public String getName() {
        return this.nameProperty().get();
    }

    @Override
    public void link() {
        this.activeRoute.changeTo(this.route);
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }
}