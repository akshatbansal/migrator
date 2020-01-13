package migrator.app.domain.index.service;

import migrator.app.boot.Container;
import migrator.app.domain.index.action.IndexCreateHandler;
import migrator.app.domain.index.action.IndexDeselectHandler;
import migrator.app.domain.index.action.IndexRemoveHandler;
import migrator.app.domain.index.action.IndexSelectHandler;
import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.IndexProperty;
import migrator.app.service.PersistanceService;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;

public class IndexService implements Service {
    private EventDispatcher dispatcher;
    private PersistanceService<Index> indexPersistanceService;
    private PersistanceService<IndexProperty> indexPropertyPersistanceService;

    private EventHandler indexCreateHandler;
    private EventHandler indexRemoveHandler;
    private EventHandler indexSelectHandler;
    private EventHandler indexDeselectHandler;

    public IndexService(Container container) {
        this.dispatcher = container.dispatcher();

        this.indexPersistanceService = new PersistanceService<>(
            container.indexContainer().indexRepository(),
            container.indexContainer().indexStorage()
        );
        this.indexPropertyPersistanceService = new PersistanceService<>(
            container.indexContainer().indexPropertyRepository(),
            container.indexContainer().indexPropertyStorage()
        );

        this.indexCreateHandler = new IndexCreateHandler(
            container.indexContainer(),
            this.dispatcher
        );
        this.indexRemoveHandler = new IndexRemoveHandler(
            container.indexContainer()
        );
        this.indexSelectHandler = new IndexSelectHandler(
            container.indexContainer().indexStore()
        );
        this.indexDeselectHandler = new IndexDeselectHandler(
            container.indexContainer().indexStore()
        );
    }

    @Override
    public void start() {
        this.dispatcher.register("index.remove", this.indexRemoveHandler);
        this.dispatcher.register("index.create", this.indexCreateHandler);
        this.dispatcher.register("index.select", this.indexSelectHandler);
        this.dispatcher.register("index.deselect", this.indexDeselectHandler);

        this.indexPropertyPersistanceService.start();
        this.indexPersistanceService.start();
    }

    @Override
    public void stop() {
        this.dispatcher.unregister("index.remove", this.indexRemoveHandler);
        this.dispatcher.unregister("index.create", this.indexCreateHandler);
        this.dispatcher.unregister("index.select", this.indexSelectHandler);
        this.dispatcher.unregister("index.deselect", this.indexDeselectHandler);

        this.indexPropertyPersistanceService.stop();
        this.indexPersistanceService.stop();
    }
}