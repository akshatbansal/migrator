package migrator.ext.javafx.connection.route;

import javafx.scene.Node;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class ConnectionViewRoute implements RouteConnection<Connection> {
    protected ConnectionGuiKit connectionGuiKit;
    protected JavafxLayout layout;

    public ConnectionViewRoute(ConnectionGuiKit connectionGuiKit, JavafxLayout layout) {
        this.connectionGuiKit = connectionGuiKit;
        this.layout = layout;
    }

    @Override
    public void show(Connection routeData) {
        this.layout.renderSide(
            (Node) this.connectionGuiKit.createForm(routeData).getContent()
        );
    }
}