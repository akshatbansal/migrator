package migrator.ext.javafx.project.route;

import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class ProjectIndexRoute implements RouteConnection<Object> {
    protected JavafxLayout layout;
    protected ProjectGuiKit projectGuiKit;

    public ProjectIndexRoute(JavafxLayout layout, ProjectGuiKit projectGuiKit) {
        this.layout = layout;
        this.projectGuiKit = projectGuiKit;
    }

    @Override
    public void show(Object routeData) {
        this.layout.renderBody(
            this.projectGuiKit.createList()
        );
        this.layout.clearSide();
    }
}