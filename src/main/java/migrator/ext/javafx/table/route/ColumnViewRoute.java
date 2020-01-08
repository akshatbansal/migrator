package migrator.ext.javafx.table.route;

import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;
import migrator.lib.factory.Factory;
import migrator.lib.factory.SingletonCallbackFactory;

public class ColumnViewRoute extends GuiNodeConnection<Column> {
    protected JavafxLayout layout;
    protected Factory<ColumnForm> columnFormFactory;

    public ColumnViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;
        this.columnFormFactory = new SingletonCallbackFactory<ColumnForm>(() -> {
            return tableGuiKit.createColumnForm();
        });
    }

    @Override
    public void show(Column routeData) {
        this.layout.renderSide(this.columnFormFactory.create());
    }
}