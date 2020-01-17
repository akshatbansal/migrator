package migrator.app.gui.action;

import javafx.application.Platform;
import migrator.app.gui.route.Route;
import migrator.app.gui.service.route.RouteStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class RouteChangeHandler implements EventHandler {
    private RouteStore routeStore;

    public RouteChangeHandler(RouteStore routeStore) {
        this.routeStore = routeStore;
    }

    @Override
    public void handle(Event<?> event) {
        Route route = (Route) event.getValue();
        Platform.runLater(() -> {
            this.routeStore.activate(route.getName());
        });
    }
}