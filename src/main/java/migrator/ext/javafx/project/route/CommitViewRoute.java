package migrator.ext.javafx.project.route;

import migrator.app.domain.project.model.Project;
import migrator.app.router.GuiNodeConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class CommitViewRoute extends GuiNodeConnection<Project> {
    protected JavafxLayout layout;

    public CommitViewRoute(JavafxLayout layout) {
        this.layout = layout;
    }

    @Override
    public void show(Project routeData) {
        // this.layout.renderBody(
        //     this.projectGuiKit.createCommitView(routeData)
        // );
        // this.layout.renderSide(
        //     this.projectGuiKit.createCommitForm(routeData)
        // );
    }
}