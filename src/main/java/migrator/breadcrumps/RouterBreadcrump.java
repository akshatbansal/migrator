package migrator.breadcrumps;

import migrator.router.Router;

public class RouterBreadcrump implements Breadcrump {
    protected String name;
    protected String routeName;
    protected Object routeData;
    protected Router router;

    public RouterBreadcrump(Router router, String name, String routeName) {
        this(router, name, routeName, null);
    }

    public RouterBreadcrump(Router router, String name, String routeName, Object routeData) {
        this.router = router;
        this.name = name;
        this.routeName = routeName;
        this.routeData = routeData;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void link() {
        this.router.show(this.routeName, this.routeData);
    }
}