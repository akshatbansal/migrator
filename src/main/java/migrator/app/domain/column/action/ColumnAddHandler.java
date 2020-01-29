package migrator.app.domain.column.action;

import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.table.model.Column;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ColumnAddHandler implements EventHandler {
    private ColumnContainer columnContainer;

    public ColumnAddHandler(ColumnContainer columnContainer) {
        this.columnContainer = columnContainer;
    }

    @Override
    public void handle(Event<?> event) {
        Column column = (Column) event.getValue();
        this.columnContainer.columnStore().add(column);
        this.columnContainer.columnRepository().addWith(column);
    }
}