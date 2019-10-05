package migrator.javafx.router;

import javafx.scene.Node;
import migrator.app.BusinessLogic;
import migrator.breadcrumps.RouterBreadcrump;
import migrator.javafx.breadcrumps.BreadcrumpsService;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.component.DatabaseList;
import migrator.app.domain.database.service.GuiKit;
import migrator.router.Route;
import migrator.router.Router;

public class DatabasesRoute implements Route {
    protected MainRenderer reneder;
    protected GuiKit guiKit;
    protected DatabaseList list;
    protected BreadcrumpsService breadcrumpsService;

    public DatabasesRoute(MainRenderer renderer, Container container, Gui gui) {
        this.reneder = renderer;
        this.guiKit = gui.getDatabaseKit();
        this.list = this.guiKit.createList();
        this.breadcrumpsService = container.getBreadcrumpsService();
    }

    @Override
    public void show(Object routeData) {
        Connection routeDataConnection = (Connection) routeData;

        // this.breadcrumpsService.set(
        //     new RouterBreadcrump(this.router, "Home", "connections"),
        //     new RouterBreadcrump(this.router, routeDataConnection.getName(), "databases", routeData)
        // );
        this.reneder.replaceCenter((Node) this.list.getContent());
    }
}