package migrator.ext.javafx.database.component;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import migrator.app.domain.database.component.DatabaseCard;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxDatabaseCard extends ViewComponent implements DatabaseCard {
    @FXML protected Text cardName;
    protected DatabaseConnection connection;
    protected Emitter<DatabaseConnection> emitter;

    public JavafxDatabaseCard(DatabaseConnection connection, ViewLoader viewLoader) {
        super(viewLoader);
        this.connection = connection;
        this.emitter = new EventEmitter<>();

        this.loadView("/layout/database/card.fxml");
    }

    @FXML public void initialize() {
        this.cardName.textProperty().bind(this.connection.databaseProperty());
    }

    @FXML public void connect() {
        this.emitter.emit("connect", this.connection);
    }

    @Override
    public Subscription<DatabaseConnection> onConnect(Subscriber<DatabaseConnection> subscriber) {
        return this.emitter.on("connect", subscriber);
    }
}