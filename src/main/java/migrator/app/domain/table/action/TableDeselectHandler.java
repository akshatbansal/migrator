package migrator.app.domain.table.action;

import migrator.app.domain.project.service.ProjectStore;
import migrator.app.domain.table.model.Table;
import migrator.app.service.SelectableStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;

public class TableDeselectHandler implements EventHandler {
    private SelectableStore<Table> tableStore;
    private EventDispatcher dispatcher;
    private ProjectStore projectStore;

    public TableDeselectHandler(SelectableStore<Table> tableStore, EventDispatcher dispatcher, ProjectStore projectStore) {
        this.tableStore = tableStore;
        this.dispatcher = dispatcher;
        this.projectStore = projectStore;
    }

    @Override
    public void handle(Event<?> event) {
        this.tableStore.deselect();
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.deselect")
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("index.deselect")
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.refresh", this.projectStore.getOpened().get())
        );
    }
}