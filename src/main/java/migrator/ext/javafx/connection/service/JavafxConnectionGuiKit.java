package migrator.ext.javafx.connection.service;

import javafx.collections.ObservableList;
import migrator.app.Container;
import migrator.app.domain.connection.component.ConnectionCard;
import migrator.app.domain.connection.component.ConnectionForm;
import migrator.app.domain.connection.component.ConnectionList;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.connection.component.JavafxConnectionCard;
import migrator.ext.javafx.connection.component.JavafxConnectionForm;
import migrator.ext.javafx.connection.component.JavafxConnectionList;

public class JavafxConnectionGuiKit implements ConnectionGuiKit {
    protected Container container;
    protected ViewLoader viewLoader;

    public JavafxConnectionGuiKit(Container container, ViewLoader viewLoader) {
        this.container = container;
        this.viewLoader = viewLoader;
    }

    @Override
    public ConnectionCard createCard(Connection connection) {
        return new JavafxConnectionCard(connection);
    }

    @Override
    public ConnectionForm createForm(Connection connection) {
        return new JavafxConnectionForm(
            this.viewLoader,
            connection,
            this.container
        );
    }

    @Override
    public ConnectionList createList(ObservableList<Connection> connections) {
        return new JavafxConnectionList(
            this.viewLoader,
            this,
            this.container
        );
    }
}