package migrator.app.breadcrumps;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.router.ActiveRoute;
import migrator.app.router.Route;

public class ActiveRouteBreadcrump implements Breadcrump {
    protected StringProperty name;
    protected ActiveRoute activeRoute;
    protected Route link;

    public ActiveRouteBreadcrump(StringProperty name, Route link, ActiveRoute activeRoute) {
        this.name = name;
        this.activeRoute = activeRoute;
        this.link = link;
    }

    public ActiveRouteBreadcrump(String name, Route link, ActiveRoute activeRoute) {
        this(new SimpleStringProperty(name), link, activeRoute);
    }

    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }

    @Override
    public void link() {
        this.activeRoute.changeTo(this.link.getName(), this.link.getValue());
    }
}