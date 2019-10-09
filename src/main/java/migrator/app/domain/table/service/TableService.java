package migrator.app.domain.table.service;

import migrator.app.domain.table.model.Table;
import migrator.lib.modelstorage.ActiveState;

public interface TableService {
    public void start();
    public void stop();
    public TableFactory getFactory();
    public ActiveState<Table> getActiveState();
    public TableRepository getRepository();
}