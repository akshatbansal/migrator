package migrator.app.domain.column.service;

import javafx.collections.ObservableList;
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
    public void remove(Column column);
    public void activate(Column column);
    public void addAndActivate(Column column);
    public ObservableList<Column> getAll();
}