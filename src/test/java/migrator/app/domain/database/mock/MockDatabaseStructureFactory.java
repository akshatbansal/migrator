package migrator.app.domain.database.mock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.ConnectionResult;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;

public class MockDatabaseStructureFactory implements DatabaseStructureFactory, DatabaseTestable {
    private ConnectionResult<?> connectionResult;
    private ObservableList<ColumnProperty> columns;
    private ObservableList<IndexProperty> indexes;
    private ObservableList<TableProperty> tables;

    public MockDatabaseStructureFactory() {
        this.connectionResult = new ConnectionResult<>(true);
        this.columns = FXCollections.observableArrayList();
        this.tables = FXCollections.observableArrayList();
        this.indexes = FXCollections.observableArrayList();
    }

    @Override
    public DatabaseStructure create(String url, String user, String password) {
        return new MockDatabaseStructure(
            this.connectionResult,
            this.tables,
            this.columns,
            this.indexes
        );
    }

    @Override
    public void setColumns(ObservableList<ColumnProperty> columns) {
        this.columns = columns;
    }

    @Override
    public void setConnectionResult(ConnectionResult<?> connectionResult) {
        this.connectionResult = connectionResult;
    }

    @Override
    public void setIndexes(ObservableList<IndexProperty> indexes) {
        this.indexes = indexes;
    }

    @Override
    public void setTables(ObservableList<TableProperty> tables) {
        this.tables = tables;
    }
}