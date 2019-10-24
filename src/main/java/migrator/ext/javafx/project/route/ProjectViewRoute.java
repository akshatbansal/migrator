package migrator.ext.javafx.project.route;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class ProjectViewRoute extends GuiNodeConnection<Project> {
    protected JavafxLayout layout;
    protected ProjectGuiKit projectGuiKit;

    public ProjectViewRoute(JavafxLayout layout, ProjectGuiKit projectGuiKit) {
        this.layout = layout;
        this.projectGuiKit = projectGuiKit;
    }

    @Override
    public void show(Project routeData) {
        this.layout.renderSide(
            this.projectGuiKit.createForm(routeData)
        );
    }
}