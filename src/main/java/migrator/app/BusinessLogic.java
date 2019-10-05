package migrator.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.change.service.ChangeService;
import migrator.app.domain.change.service.TableChangeFactory;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.ColumnFactory;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.IndexFactory;
import migrator.app.domain.table.service.IndexService;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.domain.table.service.TableService;
import migrator.app.migration.model.TableChange;
import migrator.javafx.breadcrumps.BreadcrumpsService;

public class BusinessLogic {
    protected ConnectionService connectionService;
    protected DatabaseDriverManager databaseDriverManager;
    protected DatabaseService databaseService;
    protected TableService tableService;
    protected BreadcrumpsService breadcrumpsService;
    protected ColumnService columnService;
    protected IndexService indexService;
    protected ChangeService changeService;
    protected ColumnFactory columnFactory;
    protected IndexFactory indexFactory;
    protected TableFactory tableFactory;
    protected TableChangeFactory tableChangeFactory;
 
    public BusinessLogic(DatabaseDriverManager databaseDriverManager, ConnectionService connectionService) {
        this.databaseDriverManager = databaseDriverManager;
        this.connectionService = connectionService;
        this.tableChangeFactory = new TableChangeFactory();
        this.changeService = new ChangeService(this.tableChangeFactory);
        this.columnFactory = new ColumnFactory();
        this.indexFactory = new IndexFactory();
        this.tableFactory = new TableFactory(this.tableChangeFactory);
        this.databaseService = new DatabaseService();
        this.tableService = new TableService(this.changeService, this.tableFactory);
        this.columnService = new ColumnService(this.columnFactory, this.changeService, this.tableService.getSelected());
        this.indexService = new IndexService(this.indexFactory);
        this.breadcrumpsService = new BreadcrumpsService();
        

        this.connectionService.getConnected()
            .addListener((ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) -> {
                this.onConnectConnection(newValue);
            });

        this.databaseService.getConnected()
            .addListener((ObservableValue<? extends DatabaseConnection> observable, DatabaseConnection oldValue, DatabaseConnection newValue) -> {
                this.onDatabaseConnect(newValue);
            });

        this.tableService.getSelected()
            .addListener((ObservableValue<? extends Table> observable, Table oldValue, Table newValue) -> {
                this.onTableSelect(newValue);
            });
    }

    public BusinessLogic(DatabaseDriverManager databaseDriverManager) {
        this(databaseDriverManager, new ConnectionService());
    }

    protected void onConnectConnection(Connection connection) {
        // TODO: disconect previous connection
        if (connection == null) {
            return;
        }

        DatabaseDriver databaseDriver  = this.databaseDriverManager.createDriver(connection);
        databaseDriver.connect();

        List<DatabaseConnection> databases = new ArrayList<>();
        for (String databaseName : databaseDriver.getDatabases()) {
            databases.add(new DatabaseConnection(connection, databaseName));
        }

        this.databaseService.setAll(databases);
    }

    protected void onDatabaseConnect(DatabaseConnection connection) {
        // TODO: disconect previous connection
        if (connection == null) {
            return;
        }

        DatabaseDriver databaseDriver = this.databaseDriverManager.createDriver(connection);
        databaseDriver.connect();

        String databaseDotString = connection.getConnection().getName() + "." + connection.getDatabase();
        List<Table> tables = new ArrayList<>();
        for (String tableName : databaseDriver.getTables()) {
            TableChange tableChange = this.changeService.getTableChange(databaseDotString, tableName);
            if (tableChange == null) {
                tableChange = this.changeService.getTableChangeFactory()
                    .createNotChanged(tableName);
                this.changeService.addTableChange(databaseDotString, tableChange);
            }
            Table table = this.tableFactory.create(connection, tableName, tableChange);
            tables.add(table);
        }
        List<TableChange> createdTableChanges = this.changeService.getCreatedTableChanges(databaseDotString);
        for (TableChange tableChange : createdTableChanges) {
            tables.add(
                this.tableFactory.create(connection, tableChange.getOriginalName(), tableChange)
            );
        }
        this.tableService.setAll(tables);
    }

    protected void onTableSelect(Table table) {
        if (table == null) {
            return;
        }

        DatabaseDriver databaseDriver = this.databaseDriverManager.createDriver(table.getDatabase());

        this.indexService.setAll(
            this.getTransformedIndexes(
                databaseDriver.getIndexes(table.getOriginalName())
            )
        );
        
        this.columnService.setAll(
            this.getTransformedColumns(
                databaseDriver.getColumns(table.getOriginalName())
            )
        );
    }

    private Collection<Index> getTransformedIndexes(ObservableList<List<String>> rawIndexes) {
        Map<String, List<String>> indexColumnsMap = new LinkedHashMap<>();
        for (List<String> indexValues : rawIndexes) {
            String indexName = indexValues.get(0);
            if (!indexColumnsMap.containsKey(indexName)) {
                indexColumnsMap.put(indexName, new ArrayList<>());
            }
            indexColumnsMap.get(indexName).add(indexValues.get(1));
        }

        List<Index> indexes = new ArrayList<>();
        Iterator<Entry<String, List<String>>> entryIterator = indexColumnsMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<String, List<String>> entry = entryIterator.next();
            indexes.add(
                this.indexFactory.createNotChanged(entry.getKey(), entry.getValue())
            );
        }
        return indexes;
    }

    private Collection<Column> getTransformedColumns(ObservableList<List<String>> rawColumns) {
        List<Column> columns = new ArrayList<>();
        for (List<String> columnName : rawColumns) {
            String defaultValue = columnName.get(3);
            if (defaultValue == null) {
                defaultValue = "";
            }
            columns.add(
                this.columnFactory.createNotChanged(
                    columnName.get(0),
                    columnName.get(1),
                    defaultValue,
                    columnName.get(2) == "YES" ? true : false
                )
            );
        }
        return columns;
    }

    public ConnectionService getConnection() {
        return this.connectionService;
    }

    public DatabaseService getDatabase() {
        return this.databaseService;
    }

    public TableService getTable() {
        return this.tableService;
    }

    public ColumnService getColumn() {
        return this.columnService;
    }

    public IndexService getIndex() {
        return this.indexService;
    }

    public BreadcrumpsService getBreadcrumps() {
        return this.breadcrumpsService;
    }

    public ChangeService getChange() {
        return this.changeService;
    }
}