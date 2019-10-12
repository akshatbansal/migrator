package migrator.ext.javafx.table.component;

import migrator.app.domain.table.model.Table;
import migrator.ext.javafx.component.card.Card;
import migrator.ext.javafx.component.card.CardFactory;

public class TableCardFactory implements CardFactory<Table> {
    @Override
    public Card<Table> create(Table value) {
        return new Card<Table>(
            value,
            value.nameProperty(),
            "edit",
            null,
            value.getChange().getCommand().typeProperty()
        );
    }
}