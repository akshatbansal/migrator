package migrator.app.domain.column.service;

import migrator.app.boot.Container;
import migrator.app.domain.column.action.ColumnCreateCreatedAtHandler;
import migrator.app.domain.column.action.ColumnCreateHandler;
import migrator.app.domain.column.action.ColumnCreateIdHandler;
import migrator.app.domain.column.action.ColumnCreateModifiedAtHandler;
import migrator.app.domain.column.action.ColumnDeselectHandler;
import migrator.app.domain.column.action.ColumnRemoveHandler;
import migrator.app.domain.column.action.ColumnRestoreHandler;
import migrator.app.domain.column.action.ColumnSelectHandler;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.service.PersistanceService;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;

public class ColumnService implements Service {
    private EventDispatcher dispatcher;
    private PersistanceService<Column> columnPersistanceService;
    private PersistanceService<ColumnProperty> columnPropertyPersistanceService;

    private EventHandler columnCreateIdHandler;
    private EventHandler columnCreateCreatedAtHandler;
    private EventHandler columnCreateModifiedAtHandler;
    private EventHandler columnCreateHandler;
    private EventHandler columnSelectHandler;
    private EventHandler columnDeselectHandler;
    private EventHandler columnRemoveHandler;
    private EventHandler columnRestoreHandler;

    public ColumnService(Container container) {
        this.dispatcher = container.dispatcher();

        this.columnPropertyPersistanceService = new PersistanceService<>(container.columnContainer().columnPropertyRepository(), container.columnContainer().columnPropertyStorage());
        this.columnPersistanceService = new PersistanceService<>(container.columnContainer().columnRepository(), container.columnContainer().columnStorage());

        this.columnCreateIdHandler = new ColumnCreateIdHandler(container.columnContainer(), this.dispatcher);
        this.columnCreateCreatedAtHandler = new ColumnCreateCreatedAtHandler(container.columnContainer(), this.dispatcher);
        this.columnCreateModifiedAtHandler = new ColumnCreateModifiedAtHandler(container.columnContainer(), this.dispatcher);
        this.columnCreateHandler = new ColumnCreateHandler(container.columnContainer(), this.dispatcher);
        this.columnSelectHandler = new ColumnSelectHandler(container.columnContainer().columnStore());
        this.columnDeselectHandler = new ColumnDeselectHandler(container.columnContainer().columnStore());
        this.columnRemoveHandler = new ColumnRemoveHandler(container.columnContainer());
        this.columnRestoreHandler = new ColumnRestoreHandler();
    }

    @Override
    public void start() {
        this.dispatcher.register("column.create.id", this.columnCreateIdHandler);
        this.dispatcher.register("column.create.created_at", this.columnCreateCreatedAtHandler);
        this.dispatcher.register("column.create.modified_at", this.columnCreateModifiedAtHandler);
        this.dispatcher.register("column.create", this.columnCreateHandler);
        this.dispatcher.register("column.select", this.columnSelectHandler);
        this.dispatcher.register("column.deselect", this.columnDeselectHandler);
        this.dispatcher.register("column.remove", this.columnRemoveHandler);
        this.dispatcher.register("column.restore", this.columnRestoreHandler);

        this.columnPropertyPersistanceService.start();
        this.columnPersistanceService.start();
    }

    @Override
    public void stop() {
        this.columnPropertyPersistanceService.stop();
        this.columnPersistanceService.stop();

        this.dispatcher.unregister("column.create.id", this.columnCreateIdHandler);
        this.dispatcher.unregister("column.create.created_at", this.columnCreateCreatedAtHandler);
        this.dispatcher.unregister("column.create.modified_at", this.columnCreateModifiedAtHandler);
        this.dispatcher.unregister("column.create", this.columnCreateHandler);
        this.dispatcher.unregister("column.select", this.columnSelectHandler);
        this.dispatcher.unregister("column.deselect", this.columnDeselectHandler);
        this.dispatcher.unregister("column.remove", this.columnRemoveHandler);
        this.dispatcher.unregister("column.restore", this.columnRestoreHandler);
    }
}