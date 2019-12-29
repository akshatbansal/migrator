package migrator.app.router;

public class ActiveRouteConnection<T> implements RouteConnection<T> {
    protected boolean active;

    public ActiveRouteConnection() {
        this.active = false;
    }

    @Override
    public void hide() {
        this.active = false;
    }

    @Override
    public void show(T routeData) {
        this.active = true;
    }

    protected boolean isActive() {
        return this.active;
    }
}