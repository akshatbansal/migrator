package migrator.app;

import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.domain.database.service.GuiKit;

public interface Gui {
    public ConnectionGuiKit getConnectionKit();
    public GuiKit getDatabaseKit();
    public migrator.table.service.GuiKit getTableKit();
    public migrator.breadcrumps.GuiKit getBreadcrumps();
    public migrator.change.service.GuiKit getChangeKit();
}