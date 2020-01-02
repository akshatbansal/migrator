package migrator.ext.mysql.database;

import java.util.Hashtable;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.column.ColumnStructure;
import migrator.app.database.index.DatabaseIndexDriver;
import migrator.app.database.index.IndexStructure;
import migrator.app.database.ConnectionResult;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.table.TableStructure;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;
import migrator.ext.mysql.database.column.MysqlColumnAdapter;
import migrator.ext.mysql.database.column.MysqlColumnDriver;
import migrator.ext.mysql.database.index.MysqlIndexAdapter;
import migrator.ext.mysql.database.index.MysqlIndexDriver;
import migrator.ext.mysql.database.table.MysqlTableAdapter;
import migrator.ext.mysql.database.table.MysqlTableDriver;

public class MysqlStructure implements DatabaseStructure {
    protected ObservableList<TableProperty> tables;
    protected Map<String, ObservableList<ColumnProperty>> columns;
    protected Map<String, ObservableList<IndexProperty>> indexes;

    protected MysqlConnection mysqlConnection;
    protected DatabaseIndexDriver mysqlIndexDriver;
    protected TableStructure mysqlTableStructure;
    protected ColumnStructure columnStructure;
    protected Map<String, IndexStructure> indexStructures;

    public MysqlStructure(MysqlConnection mysqlConnection) {
        this.tables = FXCollections.observableArrayList();
        this.columns = new Hashtable<>();
        this.indexes = new Hashtable<>();
        this.mysqlConnection = mysqlConnection;

        this.mysqlIndexDriver = new MysqlIndexDriver(this.mysqlConnection);

        this.mysqlTableStructure = new TableStructure(
            new MysqlTableDriver(this.mysqlConnection),
            new MysqlTableAdapter()
        );
        this.columnStructure = new ColumnStructure(
            new MysqlColumnDriver(this.mysqlConnection),
            new MysqlColumnAdapter()
        );
        this.indexStructures = new Hashtable<>();
    }

    private IndexStructure getOrCreateIndexStructure(String tableName) {
        if (!this.indexStructures.containsKey(tableName)) {
            this.indexStructures.put(
                tableName,
                new IndexStructure(
                    this.mysqlIndexDriver, 
                    new MysqlIndexAdapter(
                        this.getOrCreateColumns(tableName)
                    )
                )
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
            this.mysqlTableStructure.get()
        );
        return this.tables;
    }

    @Override
    public ConnectionResult<?> testConnection() {
        return this.mysqlConnection.connect();
    }
}