package migrator.javafx.router;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.router.Route;

public class ColumnRoute implements Route {
    protected MainRenderer renerer;
    protected TableGuiKit tableGuiKit;
    protected ColumnService columnService;
    protected ColumnForm form;

    public ColumnRoute(MainRenderer renderer, Container container, Gui gui) {
        this.renerer = renderer;
        this.tableGuiKit = gui.getTableKit();
        this.columnService = container.getColumnService();
    }

    @Override
    public void show(Object routeData) {
        if (routeData == null) {
            this.renerer.hideLeft();
            return;
        }
        this.form = this.tableGuiKit.createColumnForm(this.columnService);
        VBox.setVgrow((Node) this.form.getContent(), Priority.ALWAYS);
        this.form.setColumn((Column) routeData);
        this.renerer.replaceLeft((Node) this.form.getContent());
    }
}