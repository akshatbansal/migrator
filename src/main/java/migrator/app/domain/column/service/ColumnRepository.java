package migrator.app.domain.column.service;

import java.util.List;

import migrator.app.domain.table.model.Column;
import migrator.lib.modelstorage.ObservableListRepository;

public class ColumnRepository extends ObservableListRepository<Column> {
    public ColumnRepository() {
        super();
    }

    @Override
    public Column get(String key, String id) {
        List<Column> keyColumns = this.getOrCreateList(key);
        for (Column column : keyColumns) {
            if (column.getOriginal().getName().equals(id)) {
                return column;
            }
        }
        return null;
    }

    @Override
    public Column getOrCreate(String key, Column value) {
        Column column = this.get(key, value.getOriginal().getName());
        if (column == null) {
            this.add(key, value);
            return value;
        }
        return column;
    }
}