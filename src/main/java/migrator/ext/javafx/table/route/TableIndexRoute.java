package migrator.ext.javafx.table.route;

import java.util.List;

import javafx.application.Platform;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.SimpleConnection;
import migrator.ext.javafx.component.JavafxLayout;
import migrator.lib.factory.Factory;
import migrator.lib.factory.SingletonCallbackFactory;

public class TableIndexRoute extends SimpleConnection<List<Table>> {
    protected JavafxLayout layout;

    protected Factory<TableList> tableListFactory;
    
    public TableIndexRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;

        this.tableListFactory = new SingletonCallbackFactory<TableList>(() -> {
            return tableGuiKit.createList();
        });
    }

    @Override
    public void show(List<Table> routeData) {
        Platform.runLater(() -> {
            this.layout.clearSide();
            this.layout.renderBody(this.tableListFactory.create());
        });
    }
}