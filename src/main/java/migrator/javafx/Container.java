package migrator.javafx;

import migrator.BusinessLogic;
import migrator.Gui;
import migrator.router.Router;

public class Container {
    protected BusinessLogic businessLogic;
    protected Gui gui;
    protected Router router;

    public Container(BusinessLogic businessLogic, Gui gui, Router router) {
        this.businessLogic = businessLogic;
        this.gui = gui;
        this.router = router;
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
}