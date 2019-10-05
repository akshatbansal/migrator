package migrator.javafx.router;

import javafx.scene.Node;
import migrator.app.BusinessLogic;
import migrator.breadcrumps.RouterBreadcrump;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.change.service.ChangeService;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.IndexService;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.domain.table.service.TableService;
import migrator.router.Route;
import migrator.router.Router;

public class TableViewRoute implements Route {
    protected MainRenderer renderer;
    protected TableGuiKit guiKit;
    protected TableService tableService;
    protected ColumnService columnService;
    protected IndexService indexService;
    protected ChangeService changeService;

    public TableViewRoute(MainRenderer renderer, Container container, Gui gui) {
        this.renderer = renderer;
        this.guiKit = gui.getTableKit();
        this.tableService = container.getTableService();
        this.changeService = container.getChangeService();
        this.columnService = container.getColumnService();
        this.indexService = container.getIndexService();
    }

    @Override
    public void show(Object routeData) {
        Table table = (Table) routeData;
        // this.businessLogic.getBreadcrumps().set(
        //     new RouterBreadcrump(this.router, "Home", "connections"), 
        //     new RouterBreadcrump(this.router, table.getDatabase().getConnection().getName(), "databases", table.getDatabase().getConnection()),
        //     new RouterBreadcrump(this.router, table.getDatabase().getDatabase(), "tables", table.getDatabase()),
        //     new RouterBreadcrump(this.router, table.getName(), "tables.view", table)
        // );
        this.tableService.select(table);
        this.renderer.replaceCenter((Node) this.guiKit.createView(this.tableService, this.columnService, this.indexService, this.changeService).getContent());
        this.renderer.replaceLeft((Node) this.guiKit.createForm(this.tableService).getContent());
    }
}