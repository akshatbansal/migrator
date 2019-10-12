package migrator.ext.javafx.component.card.simple;

import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.component.card.Card;
import migrator.ext.javafx.component.card.CardComponent;
import migrator.ext.javafx.component.card.CardComponentFactory;

public class SimpleCardComponentFactory<T> implements CardComponentFactory<T> {
    protected ViewLoader viewLoader;

    public SimpleCardComponentFactory(ViewLoader viewLoader) {
        this.viewLoader = viewLoader;
    }

    @Override
    public CardComponent<T> create(Card<T> card) {
        return new SimpleCardComponent<>(card, this.viewLoader);
    }
}