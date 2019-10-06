package migrator.ext.javafx;

import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.domain.database.service.GuiKit;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.change.service.JavafxChangeGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.connection.service.JavafxConnectionGuiKit;
import migrator.ext.javafx.database.service.JavafxGuiKit;
import migrator.ext.javafx.table.service.JavafxTableGuiKit;
import migrator.router.Router;

public class JavafxGui implements Gui {
    protected ConnectionGuiKit connectionGuiKit;
    protected GuiKit databaseGuiKit;
    protected TableGuiKit tableGuiKit;
    protected migrator.breadcrumps.GuiKit breadcrumpsGuiKit;
    protected ChangeGuiKit changeGuiKit;
    protected ActiveRoute activeRoute;

    public JavafxGui(Container container) {
        ViewLoader viewLoader = new ViewLoader();
        this.breadcrumpsGuiKit = new migrator.javafx.breadcrumps.JavafxGuiKit(
            container.getBreadcrumpsService()
        );
        this.connectionGuiKit = new JavafxConnectionGuiKit(
            container,
            viewLoader
        );
        this.databaseGuiKit = new JavafxGuiKit(viewLoader, container, this);
        this.tableGuiKit = new JavafxTableGuiKit(viewLoader, container, this);
        this.changeGuiKit = new JavafxChangeGuiKit(viewLoader, container);
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
    public TableGuiKit getTableKit() {
        return this.tableGuiKit;
    }

    @Override
    public migrator.breadcrumps.GuiKit getBreadcrumps() {
        return this.breadcrumpsGuiKit;
    }

    @Override
    public ChangeGuiKit getChangeKit() {
        return this.changeGuiKit;
    }
}