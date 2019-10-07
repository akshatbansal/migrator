package migrator.ext.javafx;

import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.breadcrumps.BreadcrumpsGuiKit;
import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.breadcrumps.JavafxBreadcrumpsGuiKit;
import migrator.ext.javafx.change.service.JavafxChangeGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.project.service.JavafxProjectGuiKit;
import migrator.ext.javafx.table.service.JavafxTableGuiKit;

public class JavafxGui implements Gui {
    protected TableGuiKit tableGuiKit;
    protected BreadcrumpsGuiKit breadcrumpsGuiKit;
    protected ChangeGuiKit changeGuiKit;
    protected ProjectGuiKit projectGuiKit;
    protected ActiveRoute activeRoute;

    public JavafxGui(Container container, ViewLoader viewLoader) {
        this.breadcrumpsGuiKit = new JavafxBreadcrumpsGuiKit(viewLoader, container);
        this.tableGuiKit = new JavafxTableGuiKit(viewLoader, container, this);
        this.changeGuiKit = new JavafxChangeGuiKit(viewLoader, container);
        this.projectGuiKit = new JavafxProjectGuiKit(viewLoader, container);
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

    @Override
    public ProjectGuiKit getProject() {
        return this.projectGuiKit;
    }
}