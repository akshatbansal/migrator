package migrator.app;

import migrator.app.domain.connection.route.ConnectionRoute;

public class Router {
    protected ConnectionRoute connectionRoute;

    public Router(ConnectionRoute connectionRoute) {
        this.connectionRoute = connectionRoute;
    }

    public ConnectionRoute connection() {
        return this.connectionRoute;
    }
}