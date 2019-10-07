package migrator.app;

import migrator.app.breadcrumps.BreadcrumpsGuiKit;
import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.domain.database.service.DatabaseGuiKit;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.domain.table.service.TableGuiKit;

public interface Gui {
    public ConnectionGuiKit getConnectionKit();
    public DatabaseGuiKit getDatabaseKit();
    public TableGuiKit getTableKit();
    public BreadcrumpsGuiKit getBreadcrumps();
    public ChangeGuiKit getChangeKit();
    public ProjectGuiKit getProject();
}