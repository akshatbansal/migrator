package migrator.connection.service;

import migrator.connection.component.ConnectionCard;
import migrator.connection.component.ConnectionForm;
import migrator.connection.component.ConnectionList;
import migrator.connection.model.Connection;

public interface ConnectionGuiKit {
    public ConnectionCard createCard(Connection connection);
    public ConnectionForm createForm();
    public ConnectionList createList();
}