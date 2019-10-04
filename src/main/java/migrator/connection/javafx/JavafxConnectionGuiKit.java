package migrator.connection.javafx;

import migrator.app.database.driver.DatabaseDriverManager;
import migrator.connection.component.ConnectionCard;
import migrator.connection.component.ConnectionForm;
import migrator.connection.component.ConnectionList;
import migrator.connection.model.Connection;
import migrator.connection.service.ConnectionGuiKit;
import migrator.connection.service.ConnectionService;
import migrator.router.Router;

public class JavafxConnectionGuiKit implements ConnectionGuiKit {
    protected ConnectionService connectionService;
    protected Router router;
    protected DatabaseDriverManager databaseDriverManager;

    public JavafxConnectionGuiKit(ConnectionService connectionService, Router router, DatabaseDriverManager databaseDriverManager) {
        this.connectionService = connectionService;
        this.router = router;
        this.databaseDriverManager = databaseDriverManager;
    }

    @Override
    public ConnectionCard createCard(Connection connection) {
        return new JavafxConnectionCard(connection);
    }

    @Override
    public ConnectionForm createForm() {
        return new JavafxConnectionForm(this.connectionService, this.router, this.databaseDriverManager);
    }

    @Override
    public ConnectionList createList() {
        return new JavafxConnectionList(this, this.connectionService, this.router);
    }
}