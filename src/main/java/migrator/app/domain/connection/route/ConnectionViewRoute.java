package migrator.app.domain.connection.route;

import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.router.RouteConnection;
import migrator.app.router.RouteRenderer;

public class ConnectionViewRoute implements RouteConnection<Connection> {
    protected ConnectionGuiKit connectionGuiKit;
    protected RouteRenderer renderer;

    public ConnectionViewRoute(ConnectionGuiKit connectionGuiKit, RouteRenderer renderer) {
        this.connectionGuiKit = connectionGuiKit;
        this.renderer = renderer;
    }

    @Override
    public void show(Connection routeData) {
        this.renderer.render(
            this.connectionGuiKit.createForm(routeData)
        );
    }
}