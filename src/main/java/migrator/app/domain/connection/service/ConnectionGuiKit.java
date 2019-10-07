package migrator.app.domain.connection.service;

import javafx.collections.ObservableList;
import migrator.app.domain.connection.component.*;
import migrator.app.domain.connection.model.Connection;

public interface ConnectionGuiKit {
    public ConnectionCard createCard(Connection connection);
    public ConnectionForm createForm(Connection connection);
    public ConnectionList createList(ObservableList<Connection> connections);
}