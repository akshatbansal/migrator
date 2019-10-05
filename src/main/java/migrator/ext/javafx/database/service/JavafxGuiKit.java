package migrator.ext.javafx.database.service;

import migrator.app.domain.database.component.DatabaseCard;
import migrator.app.domain.database.component.DatabaseList;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.database.service.GuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.database.component.JavafxDatabaseCard;
import migrator.ext.javafx.database.component.JavafxDatabaseList;
import migrator.router.Router;

public class JavafxGuiKit implements GuiKit {
    protected DatabaseService databaseService;
    protected Router router;
    protected migrator.breadcrumps.GuiKit breadcrumpsGuiKit;
    protected ViewLoader viewLoader;

    public JavafxGuiKit(ViewLoader viewLoader, DatabaseService databaseService, Router router, migrator.breadcrumps.GuiKit breadcrumpsGuiKit) {
        this.viewLoader = viewLoader;
        this.databaseService = databaseService;
        this.router = router;
        this.breadcrumpsGuiKit = breadcrumpsGuiKit;
    }

    @Override
    public DatabaseCard createCard(DatabaseConnection databaseConnection) {
        return new JavafxDatabaseCard(this.viewLoader, databaseConnection);
    }

    @Override
    public DatabaseList createList() {
        return new JavafxDatabaseList(this.viewLoader, this.databaseService, this, this.router, this.breadcrumpsGuiKit);
    }
}