package migrator.app.domain.table.service;

import java.util.ArrayList;
import java.util.List;

import migrator.app.domain.table.TableRepository;
import migrator.app.domain.table.model.Table;
import migrator.app.router.ActiveRoute;
import migrator.lib.modelstorage.SimpleActiveState;

public class TableActiveState extends SimpleActiveState<Table> {
    protected TableRepository repository;
    protected ActiveRoute activeRoute;

    public TableActiveState(TableRepository repository, ActiveRoute activeRoute) {
        super();
        this.repository = repository;
        this.activeRoute = activeRoute;
    }

    @Override
    public void add(Table item) {
        this.repository.addWith(item);
        super.add(item);
    }

    @Override
    public void remove(Table item) {
        this.repository.removeWith(item);
        super.remove(item);
    }

    @Override
    public void activate(Table value) {
        super.activate(value);
        if (value != null) {
            this.activeRoute.changeTo("table.view", value);
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.activeRoute.changeTo("table.index");
    }

    @Override
    protected void applyFilter() {
        String searchString = this.searchProperty().get();
        List<Table> filtered = new ArrayList<>();
        for (Table item : this.fullList) {
            if (!searchString.isEmpty() && !item.getName().contains(searchString)) {
                continue;
            }
            filtered.add(item);
        }
        this.list.setAll(filtered);
    }
}