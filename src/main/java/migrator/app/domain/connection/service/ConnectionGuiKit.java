package migrator.app.domain.connection.service;

import migrator.app.domain.connection.component.*;
import migrator.app.domain.connection.model.Connection;

public interface ConnectionGuiKit {
    public ConnectionCard createCard(Connection connection);
    public ConnectionForm createForm();
    public ConnectionList createList();
}