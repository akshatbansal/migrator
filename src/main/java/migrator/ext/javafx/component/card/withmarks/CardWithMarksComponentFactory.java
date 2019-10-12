package migrator.ext.javafx.component.card.withmarks;

import migrator.app.domain.table.model.Table;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.component.card.Card;
import migrator.ext.javafx.component.card.CardComponent;
import migrator.ext.javafx.component.card.CardComponentFactory;

public class CardWithMarksComponentFactory implements CardComponentFactory<Table> {
    protected ViewLoader viewLoader;

    public CardWithMarksComponentFactory(ViewLoader viewLoader) {
        this.viewLoader = viewLoader;
    }

    @Override
    public CardComponent<Table> create(Card<Table> card) {
        return new CardWithMarksComponent(card, this.viewLoader);
    }
}