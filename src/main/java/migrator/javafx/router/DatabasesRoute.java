package migrator.javafx.router;

import javafx.scene.Node;
import migrator.BusinessLogic;
import migrator.breadcrumps.RouterBreadcrump;
import migrator.connection.model.Connection;
import migrator.database.component.DatabaseList;
import migrator.database.service.GuiKit;
import migrator.database.service.ServerKit;
import migrator.javafx.Container;
import migrator.router.Route;
import migrator.router.Router;

public class DatabasesRoute implements Route {
    protected MainRenderer reneder;
    protected GuiKit guiKit;
    protected ServerKit serverKit;
    protected BusinessLogic businessLogic;
    protected Router router;
    protected DatabaseList list;

    public DatabasesRoute(MainRenderer renderer, ServerKit serverKit, Container container) {
        this.reneder = renderer;
        this.guiKit = container.getGui().getDatabaseKit();
        this.serverKit = serverKit;
        this.businessLogic = container.getBusinessLogic();
        this.router = container.getRouter();

        this.list = this.guiKit.createList();
    }

    @Override
    public void show(Object routeData) {
        Connection routeDataConnection = (Connection) routeData;

        this.businessLogic.getBreadcrumps().set(
            new RouterBreadcrump(this.router, "Home", "connections"),
            new RouterBreadcrump(this.router, routeDataConnection.getName(), "databases", routeData)
        );
        this.reneder.replaceCenter((Node) this.list.getContent());
    }
}