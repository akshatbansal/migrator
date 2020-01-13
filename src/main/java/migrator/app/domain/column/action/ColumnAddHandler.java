package migrator.app.domain.column.action;

import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.table.model.Column;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;

public class ColumnAddHandler implements EventHandler {
    private ColumnContainer columnContainer;
    private EventDispatcher dispatcher;

    public ColumnAddHandler(ColumnContainer columnContainer, EventDispatcher dispatcher) {
        this.columnContainer = columnContainer;
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(Event<?> event) {
        Column column = (Column) event.getValue();
        this.columnContainer.columnStore().add(column);
        this.columnContainer.columnRepository().addWith(column);

        this.dispatcher.dispatch(
            new SimpleEvent<>("column.select", column)
        );
    }
}