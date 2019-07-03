package migrator.connection.javafx;

import migrator.connection.component.ConnectionCard;
import migrator.connection.component.ConnectionForm;
import migrator.connection.component.ConnectionList;
import migrator.connection.model.Connection;
import migrator.connection.service.ConnectionGuiKit;
import migrator.connection.service.ConnectionService;
import migrator.javafx.breadcrumps.BreadcrumpsService;
import migrator.router.Router;

public class JavafxConnectionGuiKit implements ConnectionGuiKit {
    protected ConnectionService connectionService;
    protected Router router;

    public JavafxConnectionGuiKit(ConnectionService connectionService, Router router) {
        this.connectionService = connectionService;
        this.router = router;
    }

    @Override
    public ConnectionCard createCard(Connection connection) {
        return new JavafxConnectionCard(connection);
    }

    @Override
    public ConnectionForm createForm(ConnectionService connectionService) {
        return new JavafxConnectionForm(connectionService, this.router);
    }

    @Override
    public ConnectionList createList(ConnectionService connectionService) {
        return new JavafxConnectionList(this, connectionService, this.router);
    }
}