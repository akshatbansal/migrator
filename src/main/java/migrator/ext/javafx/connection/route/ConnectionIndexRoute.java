package migrator.ext.javafx.connection.route;

import javafx.collections.ObservableList;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class ConnectionIndexRoute implements RouteConnection<ObservableList<Connection>> {
    protected ConnectionGuiKit connectionGuiKit;
    protected JavafxLayout layout;

    public ConnectionIndexRoute(ConnectionGuiKit connectionGuiKit, JavafxLayout layout) {
        this.connectionGuiKit = connectionGuiKit;
        this.layout = layout;
    }

    @Override
    public void show(ObservableList<Connection> routeData) {
        this.layout.renderBody(
            this.connectionGuiKit.createList(routeData)
        );
        this.layout.clearSide();
    }
}