package migrator.app;

import java.util.HashMap;
import java.util.Map;

import migrator.app.router.ActiveRoute;
import migrator.app.router.RouteConnection;

public class Router {
    protected Map<String, RouteConnection> routes;

    public Router(ActiveRoute activeRoute) {
        this.routes = new HashMap<>();

        activeRoute.routeProperty()
            .addListener((obs, ol, ne) -> {
                this.show(ne.getName(), ne.getValue());
            });
    }

    public void connect(String name, RouteConnection<?> connection) {
        this.routes.put(name, connection);
    }

    public void show(String routeName, Object routeData) {
        if (!this.routes.containsKey(routeName)) {
            System.out.println("Route does not exists '" + routeName + "'");
            return;
        }
        this.routes.get(routeName).show(routeData);
    }
}