package migrator.app;

import migrator.app.BusinessLogic;
import migrator.app.Gui;
import migrator.app.migration.Migration;
import migrator.router.Router;

public class Container {
    protected BusinessLogic businessLogic;
    protected Gui gui;
    protected Router router;
    protected Migration migration;

    public Container(BusinessLogic businessLogic, Gui gui, Router router, Migration migration) {
        this.businessLogic = businessLogic;
        this.gui = gui;
        this.router = router;
        this.migration = migration;
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
}