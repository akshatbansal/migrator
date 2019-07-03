package migrator.database.javafx;

import migrator.database.component.DatabaseCard;
import migrator.database.component.DatabaseList;
import migrator.database.model.DatabaseConnection;
import migrator.database.service.DatabaseService;
import migrator.database.service.GuiKit;
import migrator.javafx.breadcrumps.BreadcrumpsService;
import migrator.router.Router;

public class JavafxGuiKit implements GuiKit {
    protected DatabaseService databaseService;
    protected Router router;
    protected migrator.breadcrumps.GuiKit breadcrumpsGuiKit;

    public JavafxGuiKit(DatabaseService databaseService, Router router, migrator.breadcrumps.GuiKit breadcrumpsGuiKit) {
        this.databaseService = databaseService;
        this.router = router;
        this.breadcrumpsGuiKit = breadcrumpsGuiKit;
    }

    @Override
    public DatabaseCard createCard(DatabaseConnection databaseConnection) {
        return new JavafxDatabaseCard(databaseConnection);
    }

    @Override
    public DatabaseList createList() {
        return new JavafxDatabaseList(this.databaseService, this, this.router, this.breadcrumpsGuiKit);
    }
}