package migrator.router;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BasicRouter implements Router {
    protected Map<String, Route> routes;
    protected Stack<RouteHistory> history;

    public BasicRouter() {
        this.routes = new HashMap<>();
        this.history = new Stack<>();
    }

    protected RouteHistory removeCurrentHistory() {
        if (this.history.size() == 0) {
            return null;
        }
        return this.history.pop();
    }

    @Override
    public void back() {
        this.removeCurrentHistory();
        if (this.history.size() == 0) {
            System.out.println("No more history.");
            return;
        }
        this.history.peek().execute();
    }

    @Override
    public void connect(String routeName, Route route) {
        this.routes.put(routeName, route);
    }

    @Override
    public void show(String routeName, Object routeData) {
        if (!this.routes.containsKey(routeName)) {
            System.out.println("Route '" + routeName + "' does not exists.");
            return;
        }
        this.routes.get(routeName).show(routeData);
    }

    @Override
    public void show(String routeName) {
        this.show(routeName, null);
    }
}