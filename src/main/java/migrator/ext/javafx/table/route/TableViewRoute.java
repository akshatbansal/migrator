package migrator.ext.javafx.table.route;

import javafx.application.Platform;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;
import migrator.lib.factory.Factory;
import migrator.lib.factory.SingletonCallbackFactory;

public class TableViewRoute extends GuiNodeConnection<Table> {
    protected JavafxLayout layout;

    protected Factory<TableForm> tableFormFactory;
    protected Factory<TableView> tableViewFactory;

    public TableViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;
        
        this.tableFormFactory = new SingletonCallbackFactory<TableForm>(() -> {
            return tableGuiKit.createForm();
        });
        this.tableViewFactory = new SingletonCallbackFactory<TableView>(() -> {
            return tableGuiKit.createView();
        });
    }

    @Override
    public void show(Table routeData) {
        Platform.runLater(() -> {
            this.layout.renderBody(this.tableViewFactory.create());
            this.layout.renderSide(this.tableFormFactory.create());
        });
    }
}