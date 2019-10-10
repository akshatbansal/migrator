package migrator.app.domain.table.service;

import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Table;
import migrator.lib.modelstorage.ActiveState;

public interface TableService {
    public void start();
    public void stop();

    public void add(Table table);
    public void activate(Table table);
    public void remove(Table table);
    public void addAndActivate(Table table);
    public ObservableList<Table> getAll();
}