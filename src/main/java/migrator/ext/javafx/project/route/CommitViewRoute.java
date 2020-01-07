package migrator.ext.javafx.project.route;

import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.component.CommitForm;
import migrator.app.domain.project.component.CommitView;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.router.SimpleConnection;
import migrator.ext.javafx.component.JavafxLayout;
import migrator.lib.factory.Factory;
import migrator.lib.factory.SingletonCallbackFactory;

public class CommitViewRoute extends SimpleConnection<ProjectContainer> {
    protected JavafxLayout layout;
    protected ProjectGuiKit projectGuiKit;

    protected Factory<CommitView> commitViewFactory;
    protected Factory<CommitForm> commitFormFactory;

    public CommitViewRoute(ProjectGuiKit projectGuiKit, JavafxLayout layout) {
        this.layout = layout;
        
        this.commitFormFactory = new SingletonCallbackFactory<CommitForm>(() -> {
            return projectGuiKit.createCommitForm();
        });
        this.commitViewFactory = new SingletonCallbackFactory<CommitView>(() -> {
            return projectGuiKit.createCommitView();
        });
    }

    @Override
    public void show(ProjectContainer routeData) {
        this.layout.renderBody(this.commitViewFactory.create());
        this.layout.renderSide(this.commitFormFactory.create());
    }
}