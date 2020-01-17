package migrator.app.domain.table.action;

import migrator.app.domain.table.TableContainer;
import migrator.app.domain.table.model.Table;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class TableRemoveHandler implements EventHandler {
    private TableContainer tableContainer;

    public TableRemoveHandler(TableContainer tableContainer) {
        this.tableContainer = tableContainer;
    }

    @Override
    public void handle(Event<?> event) {
        if (event.getValue() == null) {
            return;
        }
        Table table = (Table) event.getValue();
        this.tableContainer.tableStore().remove(table);
        this.tableContainer.tableRepository().removeWith(table);
    }
}