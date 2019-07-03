package migrator.router;

public interface Router {
    public void back();
    public void show(String routeName);
    public void show(String routeName, Object routeData);
    public void connect(String routeName, Route route);
}