package migrator.app.domain.index.service;

import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Index;

public interface IndexService {
    public void start();
    public void stop();

    public void add(Index index);
    public void remove(Index index);
    public ObservableList<Index> getAll();
    public void addAndActivate(Index index);
    public void activate(Index index);
}