package migrator.app.domain.table.service;

import migrator.app.domain.table.model.Table;
import migrator.lib.modelstorage.ObservableListRepository;

public class TableRepository extends ObservableListRepository<Table> {
    public TableRepository() {
        super();
    }

    @Override
    public Table get(String key, String id) {
        return null;
    }

    @Override
    public Table getOrCreate(String key, Table value) {
        return null;
    }
}