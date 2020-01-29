package migrator.app.domain.column.action;

import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.table.model.Column;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;

public class ColumnCreateIdHandler implements EventHandler {
    private ColumnContainer columnContainer;
    private ColumnAddHandler columnAddHandler;

    public ColumnCreateIdHandler(ColumnContainer columnContainer) {
        this.columnContainer = columnContainer;
        this.columnAddHandler = new ColumnAddHandler(columnContainer);
    }

    @Override
    public void handle(Event<?> event) {
        String tableId = (String) event.getValue();
        Column column = this.columnContainer.columnFactory().createWithCreateChange(tableId, "id", "integer", null, false, "11", false, "", true);
        this.columnAddHandler.handle(
            new SimpleEvent<>("column.add", column)
        );
    }
}