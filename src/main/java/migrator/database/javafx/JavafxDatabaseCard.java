package migrator.database.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.database.component.DatabaseCard;
import migrator.database.model.DatabaseConnection;
import migrator.javafx.helpers.ControllerHelper;

public class JavafxDatabaseCard implements DatabaseCard {
    @FXML protected Text cardName;
    protected DatabaseConnection connection;
    protected Node node;
    protected Emitter emitter;

    public JavafxDatabaseCard(DatabaseConnection connection) {
        this.connection = connection;
        this.emitter = new EventEmitter();
        this.node = ControllerHelper.createViewNode(this, "/layout/database/card.fxml");
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML public void initialize() {
        this.cardName.textProperty().bind(this.connection.databaseProperty());
    }

    @FXML public void connect() {
        this.emitter.emit("connect", this.connection);
    }

    @Override
    public Subscription onConnect(Subscriber subscriber) {
        return this.emitter.on("connect", subscriber);
    }
}