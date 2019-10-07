package migrator.ext.javafx.database.route;

import java.util.List;

import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class DatabaseIndexRoute implements RouteConnection<List<DatabaseConnection>> {
    protected DatabaseGuiKit databaseGuiKit;
    protected JavafxLayout layout;

    public DatabaseIndexRoute(DatabaseGuiKit databaseGuiKit, JavafxLayout layout) {
        this.databaseGuiKit = databaseGuiKit;
        this.layout = layout;
    }

    @Override
    public void show(List<DatabaseConnection> routeData) {
        this.layout.renderBody(
            this.databaseGuiKit.createList()
        );
        this.layout.clearSide();
    }
}