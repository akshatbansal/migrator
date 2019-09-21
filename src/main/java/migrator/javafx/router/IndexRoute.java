package migrator.javafx.router;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import migrator.javafx.Container;
import migrator.router.Route;
import migrator.table.component.IndexForm;
import migrator.table.service.GuiKit;
import migrator.table.service.IndexService;
import migrator.table.model.Index;

public class IndexRoute implements Route {
    protected MainRenderer renerer;
    protected GuiKit tableGuiKit;
    protected IndexService indexService;
    protected IndexForm form;

    public IndexRoute(MainRenderer renderer, Container container) {
        this.renerer = renderer;
        this.tableGuiKit = container.getGui().getTableKit();
        this.indexService = container.getBusinessLogic().getIndex();
    }

    @Override
    public void show(Object routeData) {
        if (routeData == null) {
            this.renerer.hideLeft();
            return;
        }
        this.form = this.tableGuiKit.createIndexForm(this.indexService);
        VBox.setVgrow((Node) this.form.getContent(), Priority.ALWAYS);
        this.form.setIndex((Index) routeData);
        this.renerer.replaceLeft((Node) this.form.getContent());
    }
}