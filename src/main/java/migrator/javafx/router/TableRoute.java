package migrator.javafx.router;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import migrator.BusinessLogic;
import migrator.breadcrumps.RouterBreadcrump;
import migrator.database.model.DatabaseConnection;
import migrator.database.service.ServerConnection;
import migrator.javafx.Container;
import migrator.router.Route;
import migrator.router.Router;
import migrator.table.model.Table;
import migrator.table.service.GuiKit;

public class TableRoute implements Route {
    protected MainRenderer renderer;
    protected GuiKit guiKit;
    protected BusinessLogic businessLogic;
    protected Router router;

    public TableRoute(MainRenderer renderer, Container container) {
        this.renderer = renderer;
        this.guiKit = container.getGui().getTableKit();
        this.businessLogic = container.getBusinessLogic();
        this.router = container.getRouter();
    }

    @Override
    public void show(Object routeData) {
        DatabaseConnection routeDataConnection = (DatabaseConnection) routeData;

        this.businessLogic.getBreadcrumps().set(
            new RouterBreadcrump(this.router, "Home", "connections"), 
            new RouterBreadcrump(this.router, routeDataConnection.getConnection().getName(), "databases", routeDataConnection.getConnection()),
            new RouterBreadcrump(this.router, routeDataConnection.getDatabase(), "table", routeData)
        );
        this.renderer.replaceCenter((Node) this.guiKit.createList(this.businessLogic.getTable()).getContent());
    }

    protected void changeEditPane(Table table) {
        if (table == null) {
            this.renderer.hideLeft();
            return;
        }
        // Node toShow = (Node) this.guiKit.createForm(this.connectionService).getContent();
        // VBox.setVgrow(toShow, Priority.ALWAYS);
        // this.renerer.replaceLeft(toShow);
    }
}