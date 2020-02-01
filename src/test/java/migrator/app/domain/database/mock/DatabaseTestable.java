package migrator.app.domain.database.mock;

import javafx.collections.ObservableList;
import migrator.app.database.ConnectionResult;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;

public interface DatabaseTestable {
    public void setConnectionResult(ConnectionResult<?> connectionResult);
    public void setColumns(ObservableList<ColumnProperty> columns);
    public void setTables(ObservableList<TableProperty> tables);
    public void setIndexes(ObservableList<IndexProperty> indexes);
}