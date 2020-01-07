package migrator.ext.javafx.table.route;

import javafx.application.Platform;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class TableViewRoute extends GuiNodeConnection<Table> {
    protected JavafxLayout layout;

    protected TableForm tableForm;
    protected TableView tableView;

    public TableViewRoute(TableGuiKit tableGuiKit, JavafxLayout layout) {
        this.layout = layout;

        this.tableForm = tableGuiKit.createForm();
        this.tableView = tableGuiKit.createView();
    }

    @Override
    public void show(Table routeData) {
        Platform.runLater(() -> {
            this.layout.renderBody(this.tableView);
            this.layout.renderSide(this.tableForm);
        });
    }
}