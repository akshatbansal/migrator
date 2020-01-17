package migrator.app.domain.table.action;

import migrator.app.domain.table.model.Table;
import migrator.app.service.SelectableStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class TableSelectHandler implements EventHandler {
    private SelectableStore<Table> tableStore;

    public TableSelectHandler(SelectableStore<Table> tableStore) {
        this.tableStore = tableStore;
    }

    @Override
    public void handle(Event<?> event) {
        Table table = (Table) event.getValue();
        this.tableStore.select(table);
    }
}