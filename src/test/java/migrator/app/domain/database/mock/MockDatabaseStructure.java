package migrator.app.domain.database.mock;

import javafx.collections.ObservableList;
import migrator.app.database.ConnectionResult;
import migrator.app.database.DatabaseStructure;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;

public class MockDatabaseStructure implements DatabaseStructure {
    private ConnectionResult<?> connectionResult;
    private ObservableList<ColumnProperty> columns;
    private ObservableList<IndexProperty> indexes;
    private ObservableList<TableProperty> tables;

    public MockDatabaseStructure(
        ConnectionResult<?> connectionResult,
        ObservableList<TableProperty> tables,
        ObservableList<ColumnProperty> columns,
        ObservableList<IndexProperty> indexes
    ) {
        this.connectionResult = connectionResult;
        this.columns = columns;
        this.tables = tables;
        this.indexes = indexes;
    }

    @Override
    public ObservableList<ColumnProperty> getColumns(String table) {
        return this.columns;
    }

    @Override
    public ObservableList<IndexProperty> getIndexes(String table) {
        return this.indexes;
    }

    @Override
    public ObservableList<TableProperty> getTables() {
        return this.tables;
    }

    @Override
    public ConnectionResult<?> testConnection() {
        return this.connectionResult;
    }

    @Override
    public void close() throws Exception {}
}