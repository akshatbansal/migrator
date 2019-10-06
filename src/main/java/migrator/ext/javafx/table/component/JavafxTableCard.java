package migrator.ext.javafx.table.component;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.table.component.TableCard;
import migrator.app.domain.table.model.Table;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class JavafxTableCard extends ViewComponent implements TableCard {
    @FXML protected Text cardName;
    @FXML protected VBox pane;

    protected Table table;
    protected Emitter emitter;

    public JavafxTableCard(ViewLoader viewLoader, Table table) {
        super(viewLoader);
        this.table = table;
        this.emitter = new EventEmitter();

        this.loadView("/layout/table/card.fxml");
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