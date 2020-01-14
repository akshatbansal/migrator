package migrator.app.domain.index.action;

import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class IndexRestoreHandler implements EventHandler {
    @Override
    public void handle(Event<?> event) {
        Index index = (Index) event.getValue();
        if (index == null) {
            return;
        }

        index.getChange().nameProperty().set(index.getOriginal().getName());
        index.getChange().columnsProperty().setAll(index.originalColumnsProperty());
        index.getChangeCommand().setType(ChangeCommand.NONE);
    }
}