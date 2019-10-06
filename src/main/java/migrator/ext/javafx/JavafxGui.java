package migrator.ext.javafx;

import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.BreadcrumpsGuiKit;
import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.domain.database.service.DatabaseGuiKit;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.breadcrumps.JavafxBreadcrumpsGuiKit;
import migrator.ext.javafx.change.service.JavafxChangeGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.connection.service.JavafxConnectionGuiKit;
import migrator.ext.javafx.database.service.JavafxDatabaseGuiKit;
import migrator.ext.javafx.table.service.JavafxTableGuiKit;

public class JavafxGui implements Gui {
    protected ConnectionGuiKit connectionGuiKit;
    protected DatabaseGuiKit databaseGuiKit;
    protected TableGuiKit tableGuiKit;
    protected BreadcrumpsGuiKit breadcrumpsGuiKit;
    protected ChangeGuiKit changeGuiKit;
    protected ActiveRoute activeRoute;

    public JavafxGui(Container container) {
        ViewLoader viewLoader = new ViewLoader();
        this.breadcrumpsGuiKit = new JavafxBreadcrumpsGuiKit(viewLoader, container);
        this.connectionGuiKit = new JavafxConnectionGuiKit(
            container,
            viewLoader
        );
        this.databaseGuiKit = new JavafxDatabaseGuiKit(viewLoader, container, this);
        this.tableGuiKit = new JavafxTableGuiKit(viewLoader, container, this);
        this.changeGuiKit = new JavafxChangeGuiKit(viewLoader, container);
    }

    @Override
    public ConnectionGuiKit getConnectionKit() {
        return this.connectionGuiKit;
    }

    @Override
    public DatabaseGuiKit getDatabaseKit() {
        return this.databaseGuiKit;
    }

    @Override
    public TableGuiKit getTableKit() {
        return this.tableGuiKit;
    }

    @Override
    public BreadcrumpsGuiKit getBreadcrumps() {
        return this.breadcrumpsGuiKit;
    }

    @Override
    public ChangeGuiKit getChangeKit() {
        return this.changeGuiKit;
    }
}