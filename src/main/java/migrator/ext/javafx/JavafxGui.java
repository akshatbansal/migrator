package migrator.ext.javafx;

import javafx.stage.Window;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.BreadcrumpsGuiKit;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.loading.LoadingIndicator;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.breadcrumps.JavafxBreadcrumpsGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.loading.CursorLoadingIndicator;
import migrator.ext.javafx.table.service.JavafxTableGuiKit;

public class JavafxGui implements Gui {
    protected TableGuiKit tableGuiKit;
    protected BreadcrumpsGuiKit breadcrumpsGuiKit;
    protected ActiveRoute activeRoute;
    protected LoadingIndicator loadingIndicator;

    public JavafxGui(Container container, ViewLoader viewLoader, Window window) {
        this.breadcrumpsGuiKit = new JavafxBreadcrumpsGuiKit(viewLoader, container);
        this.tableGuiKit = new JavafxTableGuiKit(viewLoader, container, this);
        this.loadingIndicator = new CursorLoadingIndicator(window);
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
    public LoadingIndicator getLoadingIndicator() {
        return this.loadingIndicator;
    }
}