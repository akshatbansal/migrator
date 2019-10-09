package migrator.app.domain.index.service;

import migrator.app.domain.table.model.Index;
import migrator.lib.modelstorage.ActiveState;

public interface IndexService {
    public void start();
    public void stop();
    public IndexRepository getRepository();
    public IndexFactory getFactory();
    public ActiveState<Index> getActiveState();
}