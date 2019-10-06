package migrator.ext.javafx.table.route;

import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.RouteConnection;
import migrator.app.router.RouteRenderer;

public class TableViewRoute implements RouteConnection<Table> {
    protected TableGuiKit tableGuiKit;
    protected RouteRenderer bodyRenderer;
    protected RouteRenderer sideRenderer;

    public TableViewRoute(TableGuiKit tableGuiKit, RouteRenderer bodyRenderer, RouteRenderer sideRenderer) {
        this.tableGuiKit = tableGuiKit;
        this.bodyRenderer = bodyRenderer;
        this.sideRenderer = sideRenderer;
    }

    @Override
    public void show(Table routeData) {
        this.bodyRenderer.render(
            this.tableGuiKit.createView(routeData)
        );
        this.sideRenderer.render(
            this.tableGuiKit.createForm(routeData)
        );
    }
}