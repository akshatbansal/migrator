package migrator.app;

import migrator.app.breadcrumps.BreadcrumpsGuiKit;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.loading.LoadingIndicator;

public interface Gui {
    public TableGuiKit getTableKit();
    public BreadcrumpsGuiKit getBreadcrumps();
    public LoadingIndicator getLoadingIndicator();
}