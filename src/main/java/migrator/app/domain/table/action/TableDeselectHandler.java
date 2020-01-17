package migrator.app.domain.table.action;

import migrator.app.domain.table.model.Table;
import migrator.app.service.SelectableStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;

public class TableDeselectHandler implements EventHandler {
    private SelectableStore<Table> tableStore;
    private EventDispatcher dispatcher;

    public TableDeselectHandler(SelectableStore<Table> tableStore, EventDispatcher dispatcher) {
        this.tableStore = tableStore;
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(Event<?> event) {
        this.tableStore.deselect();
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.deselect")
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("index.deselect")
        );
    }
}