package migrator.app;

import migrator.app.BusinessLogic;
import migrator.app.Gui;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.migration.Migration;
import migrator.router.Router;

public class Container {
    protected BusinessLogic businessLogic;
    protected Gui gui;
    protected Router router;
    protected Migration migration;
    protected DatabaseDriverManager databaseDriverManager;

    public Container(
        BusinessLogic businessLogic,
        Gui gui,
        Router router,
        Migration migration,
        DatabaseDriverManager databaseDriverManager
    ) {
        this.businessLogic = businessLogic;
        this.gui = gui;
        this.router = router;
        this.migration = migration;
        this.databaseDriverManager = databaseDriverManager;
    }

    public BusinessLogic getBusinessLogic() {
        return this.businessLogic;
    }

    public Gui getGui() {
        return this.gui;
    }

    public Router getRouter() {
        return this.router;
    }

    protected Migration getMigration() {
        return this.migration;
    }

    public DatabaseDriverManager getDatabaseDriverManager() {
        return this.databaseDriverManager;
    }
}