package migrator.ext.javafx.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class CardListComponent<T> extends ViewComponent {
    protected ObservableList<T> list;
    protected Emitter<T> emitter;
    protected CardFactory<T> cardFactory;
    protected List<Subscription<Card<T>>> subscriptions;
    protected Map<T, CardComponent<T>> cards;
    protected ViewLoader viewLoader;
    protected CardComponent<T> focusedCard;

    @FXML protected FlowPane pane;

    public CardListComponent(ObservableList<T> list, CardFactory <T>cardFactory, ViewLoader viewLoader) {
        super(viewLoader);
        this.list = list;
        this.emitter = new EventEmitter<>();
        this.cards = new HashMap<>();
        this.cardFactory = cardFactory;
        this.viewLoader = viewLoader;
        this.subscriptions = new ArrayList<>();

        this.loadView("/layout/component/cardlist.fxml");

        this.list.addListener((Change<? extends T> change) -> {
            this.render();
        });
        this.render();
    }

    protected void render() {
        for (Subscription<Card<T>> s : this.subscriptions) {
            s.unsubscribe();
        }
        List<Node> childrens = new ArrayList<>();
        this.subscriptions.clear();
        this.pane.getChildren().clear();
        Iterator<T> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            T value = iterator.next();
            Card<T> card = this.cardFactory.create(value);
            CardComponent<T> cardComponent = new CardComponent<>(card, this.viewLoader);
            this.cards.put(value, cardComponent);
            childrens.add((Node) cardComponent.getContent());
            this.subscriptions.add(
                cardComponent.onPrimary((Card<T> eventData) -> {
                    this.emitter.emit("primary", eventData.getValue());
                })
            );
            this.subscriptions.add(
                cardComponent.onSecondary((Card<T> eventData) -> {
                    this.emitter.emit("secondary", eventData.getValue());
                })
            );
        }
        this.pane.getChildren().setAll(childrens);
    }

    public Subscription<T> onPrimary(Subscriber<T> subscriber) {
        return this.emitter.on("primary", subscriber);
    }

    public Subscription<T> onSecondary(Subscriber<T> subscriber) {
        return this.emitter.on("secondary", subscriber);
    }

    public void focus(T value) {
        if (this.focusedCard != null) {
            this.focusedCard.blur();
        }
        CardComponent<T> cardComponent = this.cards.get(value);
        if (cardComponent == null) {
            return;
        }
        this.focusedCard = cardComponent;
        if (this.focusedCard == null) {
            return;
        }
        this.focusedCard.focus();
    }
}