package migrator.app.database;

import java.util.Hashtable;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.column.ColumnStructure;
import migrator.app.database.index.DatabaseIndexDriver;
import migrator.app.database.index.IndexStructure;
import migrator.app.database.index.IndexStructureFactory;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.table.TableStructure;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;

public class SimpleDatabaseStructure implements DatabaseStructure {
    protected ObservableList<TableProperty> tables;
    protected Map<String, ObservableList<ColumnProperty>> columns;
    protected Map<String, ObservableList<IndexProperty>> indexes;

    protected DatabaseIndexDriver indexDriver;
    protected TableStructure tableStructure;
    protected ColumnStructure columnStructure;
    protected Map<String, IndexStructure> indexStructures;
    protected DatabaseConnectDriver<?> connectDriver;

    protected IndexStructureFactory indexStructureFactory;

    public SimpleDatabaseStructure(DatabaseConnectDriver<?> connectDriver, TableStructure tableStructure, ColumnStructure columnStructure, IndexStructureFactory indexStructureFactory) {
        this.tables = FXCollections.observableArrayList();
        this.columns = new Hashtable<>();
        this.indexes = new Hashtable<>();

        this.indexStructures = new Hashtable<>();

        this.connectDriver = connectDriver;
        this.columnStructure = columnStructure;
        this.tableStructure = tableStructure;
        this.indexStructureFactory = indexStructureFactory;
    }

    private IndexStructure getOrCreateIndexStructure(String tableName) {
        if (!this.indexStructures.containsKey(tableName)) {
            this.indexStructures.put(
                tableName,
                this.indexStructureFactory.create(this.getColumns(tableName))
            );
        }
        return this.indexStructures.get(tableName);
    }

    private ObservableList<ColumnProperty> getOrCreateColumns(String tablenName) {
        if (!this.columns.containsKey(tablenName)) {
            this.columns.put(tablenName, FXCollections.observableArrayList());
        }
        return this.columns.get(tablenName);
    }

    private ObservableList<IndexProperty> getOrCreateIndexes(String tableName) {
        if (!this.indexes.containsKey(tableName)) {
            this.indexes.put(tableName, FXCollections.observableArrayList());
        }
        return this.indexes.get(tableName);
    }

    @Override
    public ObservableList<ColumnProperty> getColumns(String table) {
        ObservableList<ColumnProperty> columns = this.getOrCreateColumns(table);
        columns.setAll(
            this.columnStructure.getForTable(table)
        );
        return columns;
    }

    @Override
    public ObservableList<IndexProperty> getIndexes(String table) {
        ObservableList<IndexProperty> indexes = this.getOrCreateIndexes(table);
        indexes.setAll(
            this.getOrCreateIndexStructure(table).getForTable(table)
        );

        return indexes;
    }

    @Override
    public ObservableList<TableProperty> getTables() {
        this.tables.setAll(
            this.tableStructure.get()
        );
        return this.tables;
    }

    @Override
    public ConnectionResult<?> testConnection() {
        return this.connectDriver.connect();
    }

    @Override
    public void close() throws Exception {
        this.connectDriver.close();
    }
}