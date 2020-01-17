package migrator.app.domain.table.action;

import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class TableRestoreHandler implements EventHandler {
    @Override
    public void handle(Event<?> event) {
        Table table = (Table) event.getValue();
        if (table == null) {
            return;
        }
        table.getChange().nameProperty().set(table.getOriginal().getName());
        table.getChangeCommand().setType(ChangeCommand.NONE);
    }
}