package migrator.app.gui.component.table;

import javafx.beans.property.SimpleObjectProperty;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.component.card.CardComponent;
import migrator.app.gui.component.card.CardComponentFactory;

public class TableCardComponentFactory implements CardComponentFactory<Table> {

    public TableCardComponentFactory() {}

    @Override
    public CardComponent<Table> create(Table item) {
        TableCardComponent tableCardComponent = new TableCardComponent();
        tableCardComponent.bind(new SimpleObjectProperty<>(item));

        return tableCardComponent;
    }
}