package migrator.ext.javafx.project.route;

import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.router.ActiveRoute;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class ProjectIndexRoute extends GuiNodeConnection<Object> {
    protected JavafxLayout layout;
    protected ProjectGuiKit projectGuiKit;
    protected ProjectService projectService;
    protected ActiveRoute activeRoute;

    public ProjectIndexRoute(JavafxLayout layout, ProjectGuiKit projectGuiKit, ProjectService projectService, ActiveRoute activeRoute) {
        this.layout = layout;
        this.projectGuiKit = projectGuiKit;
        this.projectService = projectService;
        this.activeRoute = activeRoute;
    }

    @Override
    public void show(Object routeData) {
        this.layout.renderBody(
            this.projectGuiKit.createList()
        );
        this.layout.clearSide();
        if (this.projectService.getSelected().get() != null) {
            this.activeRoute.changeTo("project.view", this.projectService.getSelected().get());
        }
    }
}