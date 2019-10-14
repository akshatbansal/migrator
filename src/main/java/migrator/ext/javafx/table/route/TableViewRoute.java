package migrator.ext.javafx.table.route;

import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class TableViewRoute implements RouteConnection<Table> {
    protected TableGuiKit tableGuiKit;
    protected JavafxLayout layout;
    protected String test;

    public TableViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.tableGuiKit = tableGuiKit;
        this.layout = layout;
    }

    @Override
    public void show(Table routeData) {
        this.test.equals("rs");
        this.layout.renderBody(
            this.tableGuiKit.createView(routeData)
        );
        this.layout.renderSide(
            this.tableGuiKit.createForm(routeData)
        );
    }
}