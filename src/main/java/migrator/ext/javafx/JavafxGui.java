package migrator.ext.javafx;

import javafx.stage.Window;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.BreadcrumpsGuiKit;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.breadcrumps.JavafxBreadcrumpsGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.project.service.JavafxProjectGuiKit;
import migrator.ext.javafx.table.service.JavafxTableGuiKit;

public class JavafxGui implements Gui {
    protected TableGuiKit tableGuiKit;
    protected BreadcrumpsGuiKit breadcrumpsGuiKit;
    protected ProjectGuiKit projectGuiKit;
    protected ActiveRoute activeRoute;

    public JavafxGui(Container container, ViewLoader viewLoader, Window window) {
        this.breadcrumpsGuiKit = new JavafxBreadcrumpsGuiKit(viewLoader, container);
        this.tableGuiKit = new JavafxTableGuiKit(viewLoader, container, this);
        this.projectGuiKit = new JavafxProjectGuiKit(viewLoader, container, window, this);
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
    public ProjectGuiKit getProject() {
        return this.projectGuiKit;
    }
}