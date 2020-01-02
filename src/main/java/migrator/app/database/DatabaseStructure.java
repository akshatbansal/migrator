package migrator.app.database;

import javafx.collections.ObservableList;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;

public interface DatabaseStructure {
    public ObservableList<ColumnProperty> getColumns(String table);
    public ObservableList<IndexProperty> getIndexes(String table);
    public ObservableList<TableProperty> getTables();
    public ConnectionResult<?> testConnection();
} 