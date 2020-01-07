package migrator.ext.javafx.table.route;

import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class IndexViewRoute extends GuiNodeConnection<Index> {
    protected JavafxLayout layout;
    protected IndexForm indexForm;

    public IndexViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;
        this.indexForm = tableGuiKit.createIndexForm();
    }

    @Override
    public void show(Index routeData) {
        this.layout.renderSide(this.indexForm);
    }
}