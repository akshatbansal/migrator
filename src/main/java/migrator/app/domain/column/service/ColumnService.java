package migrator.app.domain.column.service;

import migrator.app.domain.table.model.Column;
import migrator.lib.modelstorage.ActiveState;
import migrator.lib.modelstorage.Repository;

public interface ColumnService {
    public void start();
    public void stop();
    public Repository<Column> getRepository();
    public ActiveState<Column> getActiveState();
    public ColumnFactory getFactory();
    public void add(Column column);
}