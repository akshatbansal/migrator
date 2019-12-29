package migrator.ext.javafx.table.route;

import java.util.List;

import javafx.application.Platform;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.table.TableController;
import migrator.app.gui.table.TableListView;
import migrator.app.router.ActiveRouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class TableIndexRoute extends ActiveRouteConnection<List<Table>> {
    protected JavafxLayout layout;

    protected TableListView tableListView;
    
    public TableIndexRoute(JavafxLayout layout, TableController controller) {
        this.layout = layout;
        this.tableListView = new TableListView(controller);
    }

    @Override
    public void show(List<Table> routeData) {
        Platform.runLater(() -> {
            this.layout.clearSide();
            this.layout.renderBody(
                this.tableListView.getNode()
            );
        });
    }
}