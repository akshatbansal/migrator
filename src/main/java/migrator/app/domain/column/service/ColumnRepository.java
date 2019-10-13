package migrator.app.domain.column.service;

import migrator.app.domain.table.model.Column;
import migrator.lib.modelstorage.ObservableListRepository;

public class ColumnRepository extends ObservableListRepository<Column> {
    public ColumnRepository() {
        super();
    }

    @Override
    protected String getId(Column value) {
        return value.getOriginal().getName();
    }
}