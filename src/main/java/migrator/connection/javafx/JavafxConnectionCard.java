package migrator.connection.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.connection.component.ConnectionCard;
import migrator.connection.model.Connection;
import migrator.javafx.helpers.ControllerHelper;

public class JavafxConnectionCard implements ConnectionCard {
    @FXML protected Text cardName;
    protected Connection connection;
    protected Node node;
    protected Emitter emitter;

    public JavafxConnectionCard(Connection connection) {
        this.connection = connection;
        this.emitter = new EventEmitter();
        this.node = ControllerHelper.createViewNode(this, "/layout/connection/card.fxml");
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML public void initialize() {
        this.cardName.textProperty().bind(this.connection.nameProperty());
    }

    @FXML public void select() {
        this.emitter.emit("select", this.connection);
    }

    @FXML public void connect() {
        this.emitter.emit("connect", this.connection);
    }

    @Override
    public Subscription onConnect(Subscriber subscriber) {
        return this.emitter.on("connect", subscriber);
    }

    @Override
    public Subscription onSelect(Subscriber subscriber) {
        return this.emitter.on("select", subscriber);
    }
}