package migrator.javafx.router;

import javafx.scene.Node;
import migrator.breadcrumps.RouterBreadcrump;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.domain.table.service.TableService;
import migrator.router.Route;

public class TableRoute implements Route {
    protected MainRenderer renderer;
    protected TableGuiKit guiKit;
    protected TableService tableService;

    public TableRoute(MainRenderer renderer, Container container, Gui gui) {
        this.renderer = renderer;
        this.guiKit = gui.getTableKit();
        this.tableService = container.getTableService();
    }

    @Override
    public void show(Object routeData) {
        DatabaseConnection routeDataConnection = (DatabaseConnection) routeData;

        // this.businessLogic.getBreadcrumps().set(
        //     new RouterBreadcrump(this.router, "Home", "connections"), 
        //     new RouterBreadcrump(this.router, routeDataConnection.getConnection().getName(), "databases", routeDataConnection.getConnection()),
        //     new RouterBreadcrump(this.router, routeDataConnection.getDatabase(), "table", routeData)
        // );
        this.renderer.replaceCenter((Node) this.guiKit.createList(this.tableService).getContent());
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