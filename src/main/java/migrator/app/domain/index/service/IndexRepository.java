package migrator.app.domain.index.service;

import migrator.app.domain.table.model.Index;
import migrator.lib.modelstorage.ObservableListRepository;

public class IndexRepository extends ObservableListRepository<Index> {
    @Override
    protected String getId(Index value) {
        return value.getOriginal().getName();
    }
}