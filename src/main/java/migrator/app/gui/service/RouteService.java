package migrator.app.gui.service;

import migrator.app.gui.action.RouteChangeHandler;
import migrator.app.gui.service.route.RouteStore;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;

public class RouteService implements Service {
    private EventDispatcher dispatcher;
    private RouteStore routeStore;

    private EventHandler routeChangeHandler;

    public RouteService(EventDispatcher dispatcher, RouteStore routeStore) {
        this.dispatcher = dispatcher;
        this.routeStore = routeStore;

        this.routeChangeHandler = new RouteChangeHandler(this.routeStore);
    }

    @Override
    public void start() {
        this.dispatcher.register("route.change", this.routeChangeHandler);
    }

    @Override
    public void stop() {
        this.dispatcher.unregister("route.change", this.routeChangeHandler);
    }
}