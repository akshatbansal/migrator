package migrator.ext.javafx.table.route;

import java.util.List;

import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class TableIndexRoute extends GuiNodeConnection<List<Table>> {
    protected JavafxLayout layout;
    protected TableGuiKit tableGuiKit;
    
    public TableIndexRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;
        this.tableGuiKit = tableGuiKit;
    }

    @Override
    public void show(List<Table> routeData) {
        TableList tableList = this.tableGuiKit.createList();
        this.layout.clearSide();
        this.guiNodes.add(tableList);
        this.layout.renderBody(tableList);
    }
}