package migrator.app.domain.column.action;

import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
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
        if (!column.getChangeCommand().isType(ChangeCommand.CREATE)) {
            column.getChangeCommand().setType(ChangeCommand.DELETE);
            return;
        }
        this.columnContainer.columnStore().remove(column);
        this.columnContainer.columnRepository().removeWith(column);
    }
}