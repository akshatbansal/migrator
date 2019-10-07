package migrator.ext.javafx.table.route;

import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class IndexViewRoute implements RouteConnection<Index> {
    protected JavafxLayout layout;
    protected TableGuiKit tableGuiKit;

    public IndexViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;
        this.tableGuiKit = tableGuiKit;
    }

    @Override
    public void show(Index routeData) {
        this.layout.renderSide(
            this.tableGuiKit.createIndexForm(routeData)
        );
    }
}