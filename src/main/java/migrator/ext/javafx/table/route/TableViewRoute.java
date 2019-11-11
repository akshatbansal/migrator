package migrator.ext.javafx.table.route;

import javafx.application.Platform;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class TableViewRoute extends GuiNodeConnection<Table> {
    protected TableGuiKit tableGuiKit;
    protected JavafxLayout layout;

    public TableViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.tableGuiKit = tableGuiKit;
        this.layout = layout;
    }

    @Override
    public void show(Table routeData) {
        Platform.runLater(() -> {
            this.layout.renderBody(
                this.tableGuiKit.createView(routeData)
            );
            this.layout.renderSide(
                this.tableGuiKit.createForm(routeData)
            );
        });
    }
}