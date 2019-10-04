package migrator.javafx.router;

import javafx.scene.Node;
import migrator.app.BusinessLogic;
import migrator.breadcrumps.RouterBreadcrump;
import migrator.app.Container;
import migrator.router.Route;
import migrator.router.Router;
import migrator.table.model.Table;
import migrator.table.service.GuiKit;

public class TableViewRoute implements Route {
    protected MainRenderer renderer;
    protected GuiKit guiKit;
    protected BusinessLogic businessLogic;
    protected Router router;

    public TableViewRoute(MainRenderer renderer, Container container) {
        this.renderer = renderer;
        this.guiKit = container.getGui().getTableKit();
        this.businessLogic = container.getBusinessLogic();
        this.router = container.getRouter();
    }

    @Override
    public void show(Object routeData) {
        Table table = (Table) routeData;
        this.businessLogic.getBreadcrumps().set(
            new RouterBreadcrump(this.router, "Home", "connections"), 
            new RouterBreadcrump(this.router, table.getDatabase().getConnection().getName(), "databases", table.getDatabase().getConnection()),
            new RouterBreadcrump(this.router, table.getDatabase().getDatabase(), "tables", table.getDatabase()),
            new RouterBreadcrump(this.router, table.getName(), "tables.view", table)
        );
        this.businessLogic.getTable().select(table);
        this.renderer.replaceCenter((Node) this.guiKit.createView(this.businessLogic.getTable(), this.businessLogic.getColumn(), this.businessLogic.getIndex(), this.businessLogic.getChange()).getContent());
        this.renderer.replaceLeft((Node) this.guiKit.createForm(this.businessLogic.getTable()).getContent());
    }
}