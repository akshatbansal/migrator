package migrator;

import migrator.connection.javafx.JavafxConnectionGuiKit;
import migrator.connection.service.ConnectionGuiKit;
import migrator.database.javafx.JavafxGuiKit;
import migrator.database.service.GuiKit;
import migrator.javafx.helpers.View;
import migrator.router.Router;

public class Gui {
    protected ConnectionGuiKit connectionGuiKit;
    protected GuiKit databaseGuiKit;
    protected migrator.table.service.GuiKit tableGuiKit;
    protected migrator.breadcrumps.GuiKit breadcrumpsGuiKit;

    public Gui(View view, Router router, BusinessLogic businessLogic) {
        this.breadcrumpsGuiKit = new migrator.javafx.breadcrumps.JavafxGuiKit(businessLogic.getBreadcrumps());
        this.connectionGuiKit = new JavafxConnectionGuiKit(businessLogic.getConnection(), router);
        this.databaseGuiKit = new JavafxGuiKit(businessLogic.getDatabase(), router, this.breadcrumpsGuiKit);
        this.tableGuiKit = new migrator.table.javafx.JavafxGuiKit(this.breadcrumpsGuiKit, router, view);
    }

    public ConnectionGuiKit getConnectionKit() {
        return this.connectionGuiKit;
    }

    public GuiKit getDatabaseKit() {
        return this.databaseGuiKit;
    }

    public migrator.table.service.GuiKit getTableKit() {
        return this.tableGuiKit;
    }

    public migrator.breadcrumps.GuiKit getBreadcrumps() {
        return this.breadcrumpsGuiKit;
    }
}