package migrator.app.domain.connection.route;

import javafx.collections.ObservableList;
import migrator.app.domain.connection.model.Connection;
import migrator.app.router.RouteConnection;

public class ConnectionRoute {
    protected RouteConnection<ObservableList<Connection>> indexRoute;
    protected RouteConnection<Connection> viewRoute;

    public ConnectionRoute(RouteConnection<ObservableList<Connection>> indexRoute, RouteConnection<Connection> viewRoute) {
        this.indexRoute = indexRoute;
        this.viewRoute = viewRoute;
    }

    public RouteConnection<ObservableList<Connection>> index() {
        return this.indexRoute;
    }

    public RouteConnection<Connection> view() {
        return this.viewRoute;
    }
}