package migrator.ext.javafx.table.route;

import java.util.List;

import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class TableIndexRoute implements RouteConnection<List<Table>> {
    protected JavafxLayout layout;
    protected TableGuiKit tableGuiKit;
    
    public TableIndexRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;
        this.tableGuiKit = tableGuiKit;
    }

    @Override
    public void show(List<Table> routeData) {
        this.layout.clearSide();
        this.layout.renderBody(this.tableGuiKit.createList());
    }
}