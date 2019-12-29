package migrator.app.gui.table;

import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import migrator.app.gui.component.card.CardComponent;
import migrator.app.gui.component.card.CardEvent;
import migrator.app.gui.component.card.CardListener;
import migrator.ext.javafx.component.ViewLoader;

public class TableCardComponent implements CardComponent<TableGuiModel> {
    protected TableGuiModel table;
    protected Node node;
    protected List<CardListener<TableGuiModel>> listeners;

    @FXML protected Text name;

    public TableCardComponent(TableGuiModel table) {
        this.table = table;
        this.listeners = new LinkedList<>();

        ViewLoader viewLoader = new ViewLoader();
        this.node = viewLoader.load(this, "/layout/table/card.fxml");

        this.name.textProperty().bind(table.getTableProperty().getModification().nameProperty());
    }

    @Override
    public void addListener(CardListener<TableGuiModel> listener) {
        this.listeners.add(listener);
    }

    @Override
    public Node getNode() {
        return this.node;
    }

    @FXML public void edit() {
        this.fire("edit");
    }

    private void fire(String name) {
        for (CardListener<TableGuiModel> listener : this.listeners) {
            listener.onCardEvent(new CardEvent<>(name, this.table));
        }
    }
}