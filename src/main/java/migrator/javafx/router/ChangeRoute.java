package migrator.javafx.router;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import migrator.connection.component.ConnectionForm;
import migrator.connection.component.ConnectionList;
import migrator.connection.model.Connection;
import migrator.connection.service.ConnectionGuiKit;
import migrator.connection.service.ConnectionService;
import migrator.javafx.Container;
import migrator.migration.ChangeService;
import migrator.router.Route;
import migrator.table.component.ColumnForm;
import migrator.table.service.GuiKit;

public class ChangeRoute implements Route {
    protected MainRenderer renerer;
    protected GuiKit tableGuiKit;
    protected ChangeService changeService;
    protected ColumnForm form;

    public ChangeRoute(MainRenderer renderer, Container container) {
        this.renerer = renderer;
        this.tableGuiKit = container.getGui().getTableKit();
        this.changeService = container.getBusinessLogic().getChange();
        this.form = this.tableGuiKit.createColumnForm(this.changeService);

        VBox.setVgrow((Node) this.form.getContent(), Priority.ALWAYS);

        this.changeService.getSelectedColumn().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            this.changeEditPane(newValue);
        });
    }

    @Override
    public void show(Object routeData) {
        this.changeEditPane(this.changeService.getSelectedColumn().get());
        
    }

    protected void changeEditPane(Object o) {
        if (o == null) {
            this.renerer.hideLeft();
            return;
        }
        
        this.renerer.replaceLeft((Node) this.form.getContent());
    }
}