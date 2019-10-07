package migrator.ext.javafx.table.component;

import migrator.app.domain.table.model.Table;
import migrator.ext.javafx.component.Card;
import migrator.ext.javafx.component.CardFactory;

public class TableCardFactory implements CardFactory<Table> {
    @Override
    public Card<Table> create(Table value) {
        return new Card<Table>(
            value,
            value.nameProperty(),
            "edit",
            null
        );
    }
}