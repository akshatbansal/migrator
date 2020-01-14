package migrator.app.domain.column.action;

import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ColumnRestoreHandler implements EventHandler {
    @Override
    public void handle(Event<?> event) {
        Column column = (Column) event.getValue();
        if (column == null) {
            return;
        }
        
        column.getChange().nameProperty().set(column.getOriginal().getName());
        column.getChange().formatProperty().set(column.getOriginal().getFormat());
        column.getChange().defaultValueProperty().set(column.getOriginal().getDefaultValue());
        column.getChange().nullProperty().setValue(column.getOriginal().isNullEnabled());
        column.getChange().lengthProperty().set(column.getOriginal().getLength());
        column.getChange().precisionProperty().set(column.getOriginal().getPrecision());
        column.getChange().signProperty().setValue(column.getOriginal().isSigned());
        column.getChange().autoIncrementProperty().setValue(
            column.getOriginal().isAutoIncrement()
        );

        column.getChangeCommand().setType(ChangeCommand.NONE);
    }
}