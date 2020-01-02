package migrator.app.router;

public class SimpleConnection<T> implements RouteConnection<T> {
    protected boolean active;

    public SimpleConnection() {
        this.active = false;
    }

    protected boolean isActive() {
        return this.active;
    }

    @Override
    public void hide() {
        this.active = false;
    }

    @Override
    public void show(T routeData) {
        this.active = true;
    }
}