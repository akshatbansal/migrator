package migrator.app.domain.index.action;

import migrator.app.domain.index.IndexContainer;
import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class IndexRemoveHandler implements EventHandler {
    private IndexContainer indexContainer;

    public IndexRemoveHandler(IndexContainer indexContainer) {
        this.indexContainer = indexContainer;
    }

    @Override
    public void handle(Event<?> event) {
        Index index = (Index) event.getValue();
        if (index == null) {
            return;
        }
        if (!index.getChangeCommand().isType(ChangeCommand.CREATE)) {
            index.getChangeCommand().setType(ChangeCommand.DELETE);
            return;
        }
        this.indexContainer.indexStore().remove(index);
        this.indexContainer.indexRepository().removeWith(index);
    }
}