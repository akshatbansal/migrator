package migrator.javafx.router;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.connection.component.ConnectionForm;
import migrator.app.domain.connection.component.ConnectionList;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.router.Route;

public class ConnectionsRoute implements Route {
    protected MainRenderer renerer;
    protected ConnectionGuiKit connectionGuiKit;
    protected ConnectionService connectionService;
    protected ConnectionList list;
    protected ConnectionForm form;

    public ConnectionsRoute(MainRenderer renderer, Container container, Gui gui) {
        this.renerer = renderer;
        this.connectionGuiKit = gui.getConnectionKit();
        this.connectionService = container.getConnectionService();
        this.list = this.connectionGuiKit.createList(null);
        this.form = this.connectionGuiKit.createForm(null);

        VBox.setVgrow((Node) this.form.getContent(), Priority.ALWAYS);

        this.connectionService.getSelected().addListener((ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) -> {
            this.changeEditPane(newValue);
        });
    }

    @Override
    public void show(Object routeData) {
        this.renerer.replaceCenter((Node) this.list.getContent());
        this.changeEditPane(this.connectionService.getSelected().get());
        
    }

    protected void changeEditPane(Connection connection) {
        if (connection == null) {
            this.renerer.hideLeft();
            return;
        }
        
        this.renerer.replaceLeft((Node) this.form.getContent());
    }
}