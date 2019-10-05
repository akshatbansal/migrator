package migrator.app;

import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.domain.database.service.GuiKit;
import migrator.app.domain.table.service.TableGuiKit;

public interface Gui {
    public ConnectionGuiKit getConnectionKit();
    public GuiKit getDatabaseKit();
    public TableGuiKit getTableKit();
    public migrator.breadcrumps.GuiKit getBreadcrumps();
    public ChangeGuiKit getChangeKit();
}