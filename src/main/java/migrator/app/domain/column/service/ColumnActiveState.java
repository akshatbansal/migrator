package migrator.app.domain.column.service;

import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.router.ActiveRoute;
import migrator.lib.modelstorage.SimpleActiveState;

public class ColumnActiveState extends SimpleActiveState<Column> {
    protected ColumnRepository repository;
    protected TableActiveState tableActiveState;
    protected ActiveRoute activeRoute;

    public ColumnActiveState(
        ColumnRepository repository,
        TableActiveState tableActiveState,
        ActiveRoute activeRoute
    ) {
        super();
        this.repository = repository;
        this.tableActiveState = tableActiveState;
        this.activeRoute = activeRoute;
    }

    @Override
    public void add(Column item) {
        this.repository.addWith(item);
        super.add(item);
    }

    @Override
    public void remove(Column item) {
        this.repository.removeWith(item);
        super.remove(item);
    }

    @Override
    public void activate(Column value) {
        super.activate(value);
        if (value != null) {
            this.activeRoute.changeTo("column.view", value);
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.activeRoute.changeTo("table.view", this.tableActiveState.getActive().get());
    }

    @Override
    protected void applyFilter() {
        this.list.setAll(this.fullList);
    }
}