package migrator.app.gui.component.card;

import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.ext.javafx.component.ViewLoader;

public class SimpleCardComponent<T> implements CardComponent<T> {
    protected CardGuiModel<T> card;
    protected List<CardListener<T>> listeners;
    protected Node node;

    @FXML protected Text name;
    @FXML protected VBox pane;

    public SimpleCardComponent(CardGuiModel<T> card) {
        this.card = card;
        this.listeners = new LinkedList<>();

        ViewLoader viewLoader = new ViewLoader();
        this.node = viewLoader.load(this, "/layout/component/card/card.fxml");

        this.card.attribute("selected").addListener((observable, oldValue, newValue) -> {
            this.setFocus(newValue);
        });
        this.setFocus(card.attribute("selected").get());
        this.name.textProperty().bind(this.card.nameProperty());
    }

    @Override
    public void addListener(CardListener<T> listener) {
        this.listeners.add(listener);
    }

    @Override
    public Node getNode() {
        return this.node;
    }

    @FXML
    public void open() {
        for (CardListener<T> listener : this.listeners) {
            listener.onCardEvent(new CardEvent<>("open", this.card.getValue()));
        }
    }

    @FXML
    public void edit() {
        for (CardListener<T> listener : this.listeners) {
            listener.onCardEvent(new CardEvent<>("edit", this.card.getValue()));
        }
    }

    protected void setFocus(Boolean focused) {
        if (focused) {
            this.pane.getStyleClass().add("card--active");
        } else {
            this.pane.getStyleClass().remove("card--active");
        }
    }
}