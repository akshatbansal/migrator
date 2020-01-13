package migrator.app.domain.index.action;

import migrator.app.domain.table.model.Index;
import migrator.app.service.SelectableStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class IndexSelectHandler implements EventHandler {
    private SelectableStore<Index> indexStore;

    public IndexSelectHandler(SelectableStore<Index> indexStore) {
        this.indexStore = indexStore;
    }

    @Override
    public void handle(Event<?> event) {
        Index index = (Index) event.getValue();
        if (index == null) {
            return;
        }
        this.indexStore.select(index);
    }
}