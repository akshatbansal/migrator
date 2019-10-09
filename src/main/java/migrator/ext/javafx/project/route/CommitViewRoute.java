package migrator.ext.javafx.project.route;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class CommitViewRoute implements RouteConnection
<Project> {
    protected JavafxLayout layout;
    protected ProjectGuiKit projectGuiKit;

    public CommitViewRoute(ProjectGuiKit projectGuiKit, JavafxLayout layout) {
        this.projectGuiKit = projectGuiKit;
        this.layout = layout;
    }

    @Override
    public void show(Project routeData) {
        this.layout.clearBody();
        this.layout.renderSide(
            this.projectGuiKit.createCommitForm(routeData)
        );
    }
}