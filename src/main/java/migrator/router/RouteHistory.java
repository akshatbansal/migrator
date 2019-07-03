package migrator.router;

public class RouteHistory {
    protected Route route;
    protected Object routeData;

    public RouteHistory(Route route, Object routeData) {
        this.route = route;
        this.routeData = routeData;
    }

    public void execute() {
        this.route.show(this.routeData);
    }
}