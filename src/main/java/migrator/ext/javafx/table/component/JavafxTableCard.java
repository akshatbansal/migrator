package migrator.ext.javafx.table.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.table.component.TableCard;
import migrator.app.domain.table.model.Table;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.javafx.helpers.ControllerHelper;

public class JavafxTableCard implements TableCard {
    @FXML protected Text cardName;
    @FXML protected VBox pane;
    protected Table table;
    protected Node node;
    protected Emitter emitter;

    public JavafxTableCard(Table table) {
        this.table = table;
        this.emitter = new EventEmitter();
        this.node = ControllerHelper.createViewNode(this, "/layout/table/card.fxml");
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML public void initialize() {
        this.cardName.textProperty().bind(this.table.nameProperty());
        if (this.table.getChange().getCommand().getType() != null) {
            this.pane.getStyleClass().add("card--" + this.table.getChange().getCommand().getType());
        }
    }

    @FXML public void select() {
        this.emitter.emit("select", this.table);
    }

    @Override
    public Subscription onSelect(Subscriber subscriber) {
        return this.emitter.on("select", subscriber);
    }
}