package migrator.app.domain.table.service;

import javafx.beans.value.ChangeListener;
import migrator.app.boot.Container;
import migrator.app.domain.table.action.TableDeselectHandler;
import migrator.app.domain.table.action.TableNewHandler;
import migrator.app.domain.table.action.TableRefreshHandler;
import migrator.app.domain.table.action.TableRemoveHandler;
import migrator.app.domain.table.action.TableSelectHandler;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.TableProperty;
import migrator.app.service.PersistanceService;
import migrator.app.service.SelectableStore;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;

public class TableService implements Service {
    private EventDispatcher dispatcher;
    private PersistanceService<Table> tablePersistanceService;
    private PersistanceService<TableProperty> tablePropertyPersistanceService;
    private SelectableStore<Table> tableStore;

    private EventHandler tableNewHandler;
    private EventHandler tableSelectHandler;
    private EventHandler tableRefreshHandler;
    private EventHandler tableDeselectHandler;
    private EventHandler tableRemoveHandler;
    private ChangeListener<Table> selectedTableListener;
    
    public TableService(Container container) {
        this.dispatcher = container.dispatcher();
        this.tableStore = container.tableContainer().tableStore();
        this.tablePersistanceService = new PersistanceService<>(
            container.tableContainer().tableRepository(),
            container.tableContainer().tableStorage()
        );
        this.tablePropertyPersistanceService = new PersistanceService<>(
            container.tableContainer().tablePropertyRepository(),
            container.tableContainer().tablePropertyStorage()
        );

        this.tableNewHandler = new TableNewHandler(
            container.tableContainer(),
            container.projectStore().getOpened(),
            container.dispatcher()
        );
        this.tableRefreshHandler = new TableRefreshHandler(
            container.projectStore().getOpened(),
            container.columnContainer(),
            container.indexContainer()
        );
        this.tableSelectHandler = new TableSelectHandler(
            container.tableContainer().tableStore()
        );
        this.tableDeselectHandler = new TableDeselectHandler(
            container.tableContainer().tableStore()
        );
        this.tableRemoveHandler = new TableRemoveHandler(
            container.tableContainer()
        );

        this.selectedTableListener = (observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            this.dispatcher.dispatch(
                new SimpleEvent<>("table.refresh", newValue)
            );
        };
    }

    @Override
    public void start() {
        this.dispatcher.register("table.new", this.tableNewHandler);
        this.dispatcher.register("table.select", this.tableSelectHandler);
        this.dispatcher.register("table.deselect", this.tableDeselectHandler);
        this.dispatcher.register("table.refresh", this.tableRefreshHandler);
        this.dispatcher.register("table.remove", this.tableRemoveHandler);
        this.tableStore.getSelected().addListener(this.selectedTableListener);

        this.tablePropertyPersistanceService.start();
        this.tablePersistanceService.start();
    }

    @Override
    public void stop() {
        this.dispatcher.unregister("table.new", this.tableNewHandler);
        this.dispatcher.unregister("table.select", this.tableSelectHandler);
        this.dispatcher.unregister("table.deselect", this.tableDeselectHandler);
        this.dispatcher.unregister("table.refresh", this.tableRefreshHandler);
        this.dispatcher.unregister("table.remove", this.tableRemoveHandler);
        this.tableStore.getSelected().removeListener(this.selectedTableListener);

        this.tablePropertyPersistanceService.stop();
        this.tablePersistanceService.stop();
    }
}