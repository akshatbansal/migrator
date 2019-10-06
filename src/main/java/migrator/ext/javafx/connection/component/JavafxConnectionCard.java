package migrator.ext.javafx.connection.component;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import migrator.app.domain.connection.component.ConnectionCard;
import migrator.app.domain.connection.model.Connection;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class JavafxConnectionCard extends ViewComponent implements ConnectionCard {
    @FXML protected Text cardName;

    protected Connection connection;
    protected Emitter emitter;

    public JavafxConnectionCard(Connection connection, ViewLoader viewLoader) {
        super(viewLoader);
        this.connection = connection;
        this.emitter = new EventEmitter();

        this.loadView("/layout/connection/card.fxml");
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