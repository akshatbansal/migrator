package migrator.ext.javafx.change.route;

import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.app.router.RouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class CommitViewRoute implements RouteConnection
<Object> {
    protected JavafxLayout layout;
    protected ChangeGuiKit changeGuiKit;

    public CommitViewRoute(ChangeGuiKit changeGuiKit, JavafxLayout layout) {
        this.changeGuiKit = changeGuiKit;
        this.layout = layout;
    }

    @Override
    public void show(Object routeData) {
        this.layout.clearBody();
        this.layout.renderSide(
            this.changeGuiKit.createCommitForm()
        );
    }
}