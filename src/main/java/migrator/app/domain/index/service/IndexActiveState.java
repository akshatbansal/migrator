package migrator.app.domain.index.service;

import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.router.ActiveRoute;
import migrator.lib.modelstorage.SimpleActiveState;

public class IndexActiveState extends SimpleActiveState<Index> {
    protected IndexRepository repository;
    protected TableActiveState tableActiveState;
    protected ActiveRoute activeRoute;

    public IndexActiveState(IndexRepository repository, TableActiveState tableActiveState, ActiveRoute activeRoute) {
        super();
        this.repository = repository;
        this.tableActiveState = tableActiveState;
        this.activeRoute = activeRoute;
    }

    protected String getRepositoryKey() {
        Table table = this.tableActiveState.getActive().get();
        return table.getProject().getName() + "." + table.getName();
    }

    @Override
    public void add(Index item) {
        this.repository.add(this.getRepositoryKey(), item);
        super.add(item);
    }

    @Override
    public void remove(Index item) {
        this.repository.remove(this.getRepositoryKey(), item);
        super.remove(item);
    }

    @Override
    public void activate(Index value) {
        super.activate(value);
        if (value != null) {
            this.activeRoute.changeTo("index.view", value);
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.activeRoute.changeTo("table.view", this.tableActiveState.getActive().get());
    }
}