package migrator.app.domain.connection.route;

import javafx.collections.ObservableList;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.router.RouteConnection;
import migrator.app.router.RouteRenderer;

public class ConnectionIndexRoute implements RouteConnection<ObservableList<Connection>> {
    protected ConnectionGuiKit connectionGuiKit;
    protected RouteRenderer renderer;

    public ConnectionIndexRoute(ConnectionGuiKit connectionGuiKit, RouteRenderer renderer) {
        this.connectionGuiKit = connectionGuiKit;
        this.renderer = renderer;
    }

    @Override
    public void show(ObservableList<Connection> routeData) {
        this.renderer.render(
            this.connectionGuiKit.createList(routeData)
        );
    }
}