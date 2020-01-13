package migrator.app.domain.column.action;

import migrator.app.domain.table.model.Column;
import migrator.app.service.SelectableStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ColumnDeselectHandler implements EventHandler {
    private SelectableStore<Column> columnStore;

    public ColumnDeselectHandler(SelectableStore<Column> columnStore) {
        this.columnStore = columnStore;
    }

    @Override
    public void handle(Event<?> event) {
        this.columnStore.deselect();
    }
}