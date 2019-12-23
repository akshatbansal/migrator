package migrator.app.database;

import java.util.Hashtable;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.column.ColumnStructure;
import migrator.app.database.column.DatabaseColumnDriver;
import migrator.app.database.index.DatabaseIndexDriver;
import migrator.app.database.index.IndexStructure;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.table.DatabaseTableDriver;
import migrator.app.database.table.TableStructure;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;
import migrator.ext.mysql.column.MysqlColumnAdapter;
import migrator.ext.mysql.index.MysqlIndexAdapter;
import migrator.ext.mysql.table.MysqlTableAdapter;

public class SimpleDatabaseStructure implements DatabaseStructure {
    protected ObservableList<TableProperty> tables;
    protected Map<String, ObservableList<ColumnProperty>> columns;
    protected Map<String, ObservableList<IndexProperty>> indexes;

    protected DatabaseConnectDriver<?> connectDriver;
    protected DatabaseIndexDriver mysqlIndexDriver;
    protected TableStructure mysqlTableStructure;
    protected ColumnStructure columnStructure;
    protected Map<String, IndexStructure> indexStructures;

    public SimpleDatabaseStructure(DatabaseTableDriver tableDriver, DatabaseColumnDriver columnDriver, DatabaseIndexDriver indexDriver, DatabaseConnectDriver<?> connectDriver) {
        this.tables = FXCollections.observableArrayList();
        this.columns = new DefaultObservableMap<>();
        this.indexes = new DefaultObservableMap<>();
        this.connectDriver = connectDriver;

        this.mysqlIndexDriver = indexDriver;

        this.mysqlTableStructure = new TableStructure(tableDriver, new MysqlTableAdapter());
        this.columnStructure = new ColumnStructure(columnDriver, new MysqlColumnAdapter());
        this.indexStructures = new Hashtable<>();
    }

    private IndexStructure getOrCreateIndexStructure(String tableName) {
        if (!this.indexStructures.containsKey(tableName)) {
            this.indexStructures.put(
                tableName,
                new IndexStructure(
                    this.mysqlIndexDriver, 
                    new MysqlIndexAdapter(
                        this.columns.get(tableName)
                    )
                )
            );
        }
        return this.indexStructures.get(tableName);
    }

    @Override
    public ObservableList<ColumnProperty> getColumns(String table) {
        ObservableList<ColumnProperty> columns = this.columns.get(table);
        columns.setAll(
            this.columnStructure.getForTable(table)
        );
        return columns;
    }

    @Override
    public ObservableList<IndexProperty> getIndexes(String table) {
        ObservableList<IndexProperty> indexes = this.indexes.get(table);
        indexes.setAll(
            this.getOrCreateIndexStructure(table).getForTable(table)
        );
        
        return indexes;
    }

    @Override
    public ObservableList<TableProperty> getTables() {
        this.tables.setAll(
            this.mysqlTableStructure.get()
        );
        return this.tables;
    }

    public class DefaultObservableMap<T, U> extends Hashtable<T, ObservableList<U>> {
        private static final long serialVersionUID = 5093889375181995701L;

        @SuppressWarnings("unchecked")
        @Override
        public synchronized ObservableList<U> get(Object key) {
            if (!this.contains(key)) {
                this.put((T) key, FXCollections.observableArrayList());
            }
            return super.get(key);
        }
    }

    @Override
    public ConnectionResult<?> testConnection() {
        return this.connectDriver.connect();
    }
}