package migrator.app.domain.column.service;

import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Column;

public interface ColumnService {
    public void start();
    public void stop();

    public void add(Column column);
    public void remove(Column column);
    public void activate(Column column);
    public void addAndActivate(Column column);
    public ObservableList<Column> getAll();
}