package migrator.ext.javafx.connection.service;

import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.connection.component.ConnectionCard;
import migrator.app.domain.connection.component.ConnectionForm;
import migrator.app.domain.connection.component.ConnectionList;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.ext.javafx.connection.component.JavafxConnectionCard;
import migrator.ext.javafx.connection.component.JavafxConnectionForm;
import migrator.ext.javafx.connection.component.JavafxConnectionList;
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