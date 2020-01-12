package migrator.app.gui.route;

import migrator.lib.dispatcher.Event;

public class RouteChangeEvent implements Event<Route> {
    private Route route;
    private String evenName;

    public RouteChangeEvent(String routeName) {
        this.evenName = "route.change";
        this.route = new SimpleRoute(routeName);
    }

    @Override
    public String getName() {
        return this.evenName;
    }

    @Override
    public Route getValue() {
        return this.route;
    }
}