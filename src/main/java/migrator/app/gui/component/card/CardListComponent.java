package migrator.app.gui.component.card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.lib.dispatcher.Event;

public class CardListComponent<T> extends SimpleComponent implements Component {
    protected ObservableList<T> list;
    protected CardComponentFactory<T> cardComponentFactory;

    @FXML protected FlowPane pane;

    public CardListComponent(CardComponentFactory<T> cardComponentFactory) {
        super();
        this.cardComponentFactory = cardComponentFactory;

        this.loadFxml("/layout/component/cardlist.fxml");
    }

    public void bind(ObservableList<T> list) {
        this.list = list;
        this.list.addListener((Change<? extends T> change) -> {
            this.render();
        });
        this.render();
    }

    protected void render() {
        List<Node> childrens = new ArrayList<>();
        this.pane.getChildren().clear();
        Iterator<T> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            T value = iterator.next();
            CardComponent<T> cardComponent = this.cardComponentFactory.create(value);
            cardComponent.outputs().addListener((observable, oldValue, newValue) -> {
                this.forwardOutput(newValue);
            });
            childrens.add(cardComponent.getNode());
        }
        this.pane.getChildren().setAll(childrens);
    }

    private void forwardOutput(Event<?> event) {
        this.output(event);
    }
}