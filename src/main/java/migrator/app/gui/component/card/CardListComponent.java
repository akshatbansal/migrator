package migrator.app.gui.component.card;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import migrator.ext.javafx.component.ViewLoader;

public class CardListComponent<T> implements CardListener<T> {
    protected ObservableList<T> list;
    protected Map<String, ObjectProperty<CardEvent<T>>> events;
    protected CardComponentFactory<T> cardFactory;
    protected Node node;

    @FXML protected FlowPane pane;

    public CardListComponent(
        ObservableList<T> list, 
        CardComponentFactory<T> cardFactory
    ) {
        this.list = list;
        this.cardFactory = cardFactory;
        this.events = new Hashtable<>();

        ViewLoader viewLoader = new ViewLoader();
        this.node = viewLoader.load(this, "/layout/component/cardlist.fxml");

        this.list.addListener((Change<? extends T> Change) -> {
            this.render();
        });
        this.render();
    }

    protected void render() {
        List<Node> childrens = new LinkedList<>();
        this.pane.getChildren().clear();
        Iterator<T> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            T value = iterator.next();
            CardComponent<T> cardComponent = this.cardFactory.create(value);
            cardComponent.addListener(this);
            childrens.add(cardComponent.getNode());
        }
        this.pane.getChildren().setAll(childrens);
    }

    public ObjectProperty<CardEvent<T>> event(String name) {
        if (!this.events.containsKey(name)) {
            this.events.put(name, new SimpleObjectProperty<>());
        }
        return this.events.get(name);
    }

    @Override
    public void onCardEvent(CardEvent<T> event) {
        this.event(event.getName()).set(event);
    }

    public Node getNode() {
        return this.node;
    }
}