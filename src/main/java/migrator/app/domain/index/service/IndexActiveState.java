package migrator.app.domain.index.service;

import migrator.app.domain.index.IndexRepository;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexProperty;
import migrator.app.router.ActiveRoute;
import migrator.lib.modelstorage.SimpleActiveState;
import migrator.lib.repository.UniqueRepository;

public class IndexActiveState extends SimpleActiveState<Index> {
    protected IndexRepository repository;
    protected UniqueRepository<IndexProperty> indexPropertyRepo;
    protected UniqueRepository<ChangeCommand> changeCommandRepo;
    protected TableActiveState tableActiveState;
    protected ActiveRoute activeRoute;

    public IndexActiveState(
        IndexRepository repository,
        UniqueRepository<IndexProperty> indexPropertyRepo,
        UniqueRepository<ChangeCommand> changeCommandRepo,
        TableActiveState tableActiveState,
        ActiveRoute activeRoute
    ) {
        super();
        this.repository = repository;
        this.indexPropertyRepo = indexPropertyRepo;
        this.changeCommandRepo = changeCommandRepo;
        this.tableActiveState = tableActiveState;
        this.activeRoute = activeRoute;
    }

    @Override
    public void add(Index item) {
        this.changeCommandRepo.add(item.getChangeCommand());
        this.indexPropertyRepo.add(item.getOriginal());
        this.indexPropertyRepo.add(item.getChange());
        this.repository.add(item);
        super.add(item);
    }

    @Override
    public void remove(Index item) {
        this.changeCommandRepo.remove(item.getChangeCommand());
        this.indexPropertyRepo.remove(item.getOriginal());
        this.indexPropertyRepo.remove(item.getChange());
        this.repository.remove(item);
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

    @Override
    protected void applyFilter() {
        this.list.setAll(this.fullList);
    }
}