package migrator.javafx.router;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.IndexService;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.router.Route;

public class IndexRoute implements Route {
    protected MainRenderer renerer;
    protected TableGuiKit tableGuiKit;
    protected IndexService indexService;
    protected ColumnService columnService;
    protected IndexForm form;

    public IndexRoute(MainRenderer renderer, Container container) {
        this.renerer = renderer;
        this.tableGuiKit = container.getGui().getTableKit();
        this.indexService = container.getBusinessLogic().getIndex();
        this.columnService = container.getBusinessLogic().getColumn();
    }

    @Override
    public void show(Object routeData) {
        if (routeData == null) {
            this.renerer.hideLeft();
            return;
        }
        this.form = this.tableGuiKit.createIndexForm(this.indexService, this.columnService);
        VBox.setVgrow((Node) this.form.getContent(), Priority.ALWAYS);
        this.form.setIndex((Index) routeData);
        this.renerer.replaceLeft((Node) this.form.getContent());
    }
}