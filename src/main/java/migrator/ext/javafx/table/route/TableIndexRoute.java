package migrator.ext.javafx.table.route;

import java.util.List;

import javafx.application.Platform;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.SimpleConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class TableIndexRoute extends SimpleConnection<List<Table>> {
    protected JavafxLayout layout;
    protected TableGuiKit tableGuiKit;

    protected TableList tableList;
    
    public TableIndexRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;
        this.tableList = tableGuiKit.createList();
    }

    @Override
    public void show(List<Table> routeData) {
        Platform.runLater(() -> {
            this.layout.clearSide();
            this.layout.renderBody(this.tableList);
        });
    }
}