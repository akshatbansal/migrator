package migrator.ext.javafx.connection.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import migrator.app.domain.connection.component.ConnectionCard;
import migrator.app.domain.connection.component.ConnectionList;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionGuiKit;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.emitter.Subscription;
import migrator.javafx.helpers.ControllerHelper;
import migrator.router.Router;

public class JavafxConnectionList implements ConnectionList {
    protected ConnectionGuiKit connectionGuiKit;
    protected Node node;
    protected List<Subscription> subscriptions;
    protected ConnectionService connectionService;
    protected ConnectionCard selectedCard;
    protected Map<Connection, ConnectionCard> cards;
    protected Router router;

    @FXML protected FlowPane connectionsView;
    @FXML protected ScrollPane scrollPane;

    public JavafxConnectionList(ConnectionGuiKit connectionGuiKit, ConnectionService connectionService, Router router) {
        this.connectionGuiKit = connectionGuiKit;
        this.connectionService = connectionService;
        this.router = router;
        this.subscriptions = new LinkedList<>();
        this.cards = new HashMap<>();
        this.node = ControllerHelper.createViewNode(this, "/layout/connection/index.fxml");
        
        this.connectionService.getSelected().addListener((ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) -> {
            this.setSelectedCardByConnection(newValue);
        });
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    protected void draw() {
        for (Subscription s : this.subscriptions) {
            s.unsubscribe();
        }
        this.subscriptions.clear();
        this.cards.clear();
        this.connectionsView.getChildren().clear();
        Iterator<Connection> iterator = this.connectionService.getList().iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            ConnectionCard card = this.connectionGuiKit.createCard(connection);
            this.cards.put(connection, card);
            this.subscriptions.add(
                card.onSelect((Object o) -> {
                    this.connectionService.select((Connection) o);
                })
            );
            this.subscriptions.add(
                card.onConnect((Object o) -> {
                    this.connectionService.connect((Connection) o);
                    this.router.show("databases", o);
                })
            );
            this.connectionsView.getChildren().add((Node) card.getContent());
        }
    }

    protected void setSelectedCard(ConnectionCard card) {
        if (this.selectedCard != null) {
            ((Node) this.selectedCard.getContent()).getStyleClass().remove("card--active");
        }
        this.selectedCard = card;
        if (this.selectedCard == null) {
            return;
        }
        ((Node) card.getContent()).getStyleClass().add("card--active");
    }

    protected void setSelectedCardByConnection(Connection connection) {
        if (connection == null) {
            this.setSelectedCard(null);
            return;
        }
        this.setSelectedCard(this.cards.get(connection));
    }

    @FXML
    public void initialize() {
        this.connectionService.getList().addListener((Change<? extends Connection> c) -> {
            this.draw();
        });
        this.draw();
        this.setSelectedCardByConnection(this.connectionService.getSelected().get());
    }

    @FXML public void openCreateConnection() {
        Connection newConnection = new Connection("New Connection");
        newConnection.setPort("3306");
        newConnection.setDriver("mysql");
        this.connectionService.add(newConnection);
        this.connectionService.select(newConnection);
    }

    @FXML public void openCommit() {
        this.router.show("commit");
    }
}