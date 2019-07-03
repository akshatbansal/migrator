package migrator.database.service;

import migrator.database.component.DatabaseCard;
import migrator.database.component.DatabaseList;
import migrator.database.model.DatabaseConnection;

public interface GuiKit {
    public DatabaseCard createCard(DatabaseConnection databaseConnection);
    public DatabaseList createList();
}