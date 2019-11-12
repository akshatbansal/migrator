package migrator.ext.javafx.table.component;

import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActivator;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.component.card.CardComponent;
import migrator.ext.javafx.component.card.CardComponentFactory;

public class TableCardFactory implements CardComponentFactory<Table> {
    protected ViewLoader viewLoader;
    protected TableActivator tableActivator;

    public TableCardFactory(ViewLoader viewLoader, TableActivator tableActivator) {
        this.viewLoader = viewLoader;
        this.tableActivator = tableActivator;
    }

    @Override
    public CardComponent<Table> create(Table item) {
        return new TableCard(item, this.viewLoader, this.tableActivator);
    }
}