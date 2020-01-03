package migrator.ext.javafx.project.route;

import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.component.CommitForm;
import migrator.app.domain.project.component.CommitView;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.router.SimpleConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class CommitViewRoute extends SimpleConnection<ProjectContainer> {
    protected JavafxLayout layout;

    protected CommitView commitView;
    protected CommitForm commitForm;

    public CommitViewRoute(ProjectGuiKit projectGuiKit, JavafxLayout layout) {
        this.layout = layout;

        this.commitForm = projectGuiKit.createCommitForm();
        this.commitView = projectGuiKit.createCommitView();
    }

    @Override
    public void show(ProjectContainer routeData) {
        this.layout.renderBody(this.commitView);
        this.layout.renderSide(this.commitForm);
    }
}