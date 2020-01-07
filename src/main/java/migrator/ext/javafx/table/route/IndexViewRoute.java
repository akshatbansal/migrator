package migrator.ext.javafx.table.route;

import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;
import migrator.lib.factory.Factory;
import migrator.lib.factory.SingletonCallbackFactory;

public class IndexViewRoute extends GuiNodeConnection<Index> {
    protected JavafxLayout layout;
    protected Factory<IndexForm> indexFormFactory;

    public IndexViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;
        this.indexFormFactory = new SingletonCallbackFactory<IndexForm>(() -> {
            return tableGuiKit.createIndexForm();
        });
    }

    @Override
    public void show(Index routeData) {
        this.layout.renderSide(this.indexFormFactory.create());
    }
}