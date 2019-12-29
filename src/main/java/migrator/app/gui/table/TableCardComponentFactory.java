package migrator.app.gui.table;

import migrator.app.gui.component.card.CardComponent;
import migrator.app.gui.component.card.CardComponentFactory;

public class TableCardComponentFactory implements CardComponentFactory<TableGuiModel> {
    @Override
    public CardComponent<TableGuiModel> create(TableGuiModel item) {
        return new TableCardComponent(item);
    }
}