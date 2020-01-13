package migrator.app.domain.index.action;

import migrator.app.domain.index.IndexContainer;
import migrator.app.domain.table.model.Index;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;

public class IndexCreateHandler implements EventHandler {
    private IndexContainer indexContainer;
    private EventDispatcher dispatcher;

    public IndexCreateHandler(IndexContainer indexContainer, EventDispatcher dispatcher) {
        this.indexContainer = indexContainer;
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(Event<?> event) {
        String tableId = (String) event.getValue();
        Index index = this.indexContainer.indexFactory().createWithCreateChange(tableId, "index_name");
        this.indexContainer.indexStore().add(index);
        this.indexContainer.indexRepository().addWith(index);

        this.dispatcher.dispatch(
            new SimpleEvent<>("index.select", index)
        );
    }
}