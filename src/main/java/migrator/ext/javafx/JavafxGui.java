package migrator.ext.javafx;

import migrator.app.BusinessLogic;
import migrator.app.Gui;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.migration.Migration;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.connection.service.JavafxConnectionGuiKit;
import migrator.database.javafx.JavafxGuiKit;
import migrator.database.service.GuiKit;
import migrator.javafx.helpers.View;
import migrator.router.Router;

public class JavafxGui implements Gui {
    protected ConnectionGuiKit connectionGuiKit;
    protected GuiKit databaseGuiKit;
    protected migrator.table.service.GuiKit tableGuiKit;
    protected migrator.breadcrumps.GuiKit breadcrumpsGuiKit;
    protected migrator.change.service.GuiKit changeGuiKit;

    public JavafxGui(
        View view,
        Router router,
        BusinessLogic businessLogic,
        Migration migration, 
        DatabaseDriverManager databaseDriverManager
    ) {
        this.breadcrumpsGuiKit = new migrator.javafx.breadcrumps.JavafxGuiKit(businessLogic.getBreadcrumps());
        this.connectionGuiKit = new JavafxConnectionGuiKit(businessLogic.getConnection(), router, databaseDriverManager);
        this.databaseGuiKit = new JavafxGuiKit(businessLogic.getDatabase(), router, this.breadcrumpsGuiKit);
        this.tableGuiKit = new migrator.table.javafx.JavafxGuiKit(
            this.breadcrumpsGuiKit,
            router,
            view,
            businessLogic.getDatabase(),
            businessLogic.getTable()
        );
        this.changeGuiKit = new migrator.change.javafx.JavafxGuiKit(
            new ViewLoader(),
            router,
            businessLogic.getChange(),
            migration
        );
    }

    @Override
    public ConnectionGuiKit getConnectionKit() {
        return this.connectionGuiKit;
    }

    @Override
    public GuiKit getDatabaseKit() {
        return this.databaseGuiKit;
    }

    @Override
    public migrator.table.service.GuiKit getTableKit() {
        return this.tableGuiKit;
    }

    @Override
    public migrator.breadcrumps.GuiKit getBreadcrumps() {
        return this.breadcrumpsGuiKit;
    }

    @Override
    public migrator.change.service.GuiKit getChangeKit() {
        return this.changeGuiKit;
    }
}