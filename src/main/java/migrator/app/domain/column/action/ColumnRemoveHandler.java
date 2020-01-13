package migrator.app.domain.column.action;

import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.table.model.Column;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ColumnRemoveHandler implements EventHandler {
    private ColumnContainer columnContainer;

    public ColumnRemoveHandler(ColumnContainer columnContainer) {
        this.columnContainer = columnContainer;
    }
    
    @Override
    public void handle(Event<?> event) {
        Column column = (Column) event.getValue();
        if (column == null) {
            return;
        }
        this.columnContainer.columnStore().remove(column);
        this.columnContainer.columnRepository().removeWith(column);
    }
}