package migrator.app.domain.database.service;

import migrator.app.domain.database.component.DatabaseCard;
import migrator.app.domain.database.component.DatabaseList;
import migrator.app.domain.database.model.DatabaseConnection;

public interface DatabaseGuiKit {
    public DatabaseCard createCard(DatabaseConnection databaseConnection);
    public DatabaseList createList();
}