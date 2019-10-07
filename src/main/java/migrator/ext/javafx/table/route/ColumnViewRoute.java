package migrator.ext.javafx.table.route;

import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class ColumnViewRoute implements RouteConnection<Column> {
    protected TableGuiKit tableGuiKit;
    protected JavafxLayout layout;

    public ColumnViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.tableGuiKit = tableGuiKit;
        this.layout = layout;
    }

    @Override
    public void show(Column routeData) {
        this.layout.renderSide(
            this.tableGuiKit.createColumnForm(routeData)
        );
    }
}