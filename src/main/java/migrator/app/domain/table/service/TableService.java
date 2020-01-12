package migrator.app.domain.table.service;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import migrator.app.boot.Container;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.action.TableNewHandler;
import migrator.app.domain.table.action.TableRefreshHandler;
import migrator.app.domain.table.action.TableSelectHandler;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.TableProperty;
import migrator.app.service.PersistanceService;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;

public class TableService implements Service {
    private EventDispatcher dispatcher;
    private ObjectProperty<ProjectContainer> projectContainerProperty;
    private ChangeListener<ProjectContainer> projectContainerListener;
    private PersistanceService<Table> tablePersistanceService;
    private PersistanceService<TableProperty> tablePropertyPersistanceService;

    private EventHandler tableNewHandler;
    private EventHandler tableSelectHandler;
    private EventHandler tableRefreshHandler;
    
    public TableService(Container container) {
        this.dispatcher = container.dispatcher();
        this.projectContainerProperty = container.projectStore().getOpened();
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
            container.tableContainer()
        );
        this.tableSelectHandler = new TableSelectHandler(
            container.tableContainer().tableStore()
        );

        this.projectContainerListener = (ObservableValue<? extends ProjectContainer> observable, ProjectContainer oldValue, ProjectContainer newValue) -> {
            this.onProjectActivate(newValue);
        };
    }

    @Override
    public void start() {
        this.dispatcher.register("table.new", this.tableNewHandler);
        this.dispatcher.register("table.select", this.tableSelectHandler);
        this.dispatcher.register("db.table.load", this.tableRefreshHandler);
        this.projectContainerProperty.addListener(this.projectContainerListener);

        this.tablePropertyPersistanceService.start();
        this.tablePersistanceService.start();
    }

    @Override
    public void stop() {
        this.dispatcher.unregister("table.new", this.tableNewHandler);
        this.dispatcher.unregister("table.select", this.tableSelectHandler);
        this.dispatcher.unregister("db.table.load", this.tableRefreshHandler);
        this.projectContainerProperty.removeListener(this.projectContainerListener);

        this.tablePropertyPersistanceService.stop();
        this.tablePersistanceService.stop();
    }

    protected void onProjectActivate(ProjectContainer projectContainer) {
        this.dispatcher.dispatch(
            new SimpleEvent<>("db.table.load", projectContainer)
        );
    }
}