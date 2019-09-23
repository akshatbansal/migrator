package migrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import migrator.connection.model.Connection;
import migrator.connection.service.ConnectionService;
import migrator.database.model.DatabaseConnection;
import migrator.database.service.DatabaseService;
import migrator.database.service.ServerConnection;
import migrator.database.service.ServerConnectionFactory;
import migrator.javafx.breadcrumps.BreadcrumpsService;
import migrator.migration.ChangeCommand;
import migrator.migration.ChangeService;
import migrator.migration.ColumnChange;
import migrator.migration.IndexChange;
import migrator.migration.TableChange;
import migrator.table.model.Column;
import migrator.table.model.Index;
import migrator.table.model.Table;
import migrator.table.service.ColumnService;
import migrator.table.service.IndexService;
import migrator.table.service.TableService;

public class BusinessLogic {
    protected ConnectionService connectionService;
    protected DatabaseService databaseService;
    protected TableService tableService;
    protected BreadcrumpsService breadcrumpsService;
    protected ColumnService columnService;
    protected IndexService indexService;
    protected ChangeService changeService;
    protected ServerConnectionFactory serverConnectionFactory;
 
    public BusinessLogic(ServerConnectionFactory serverConnectionFactory, ConnectionService connectionService) {
        this.serverConnectionFactory = serverConnectionFactory;
        this.connectionService = connectionService;
        this.databaseService = new DatabaseService();
        this.tableService = new TableService();
        this.columnService = new ColumnService();
        this.indexService = new IndexService();
        this.breadcrumpsService = new BreadcrumpsService();
        this.changeService = new ChangeService();

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

    public BusinessLogic(ServerConnectionFactory serverConnectionFactory) {
        this(serverConnectionFactory, new ConnectionService());
    }

    protected void onConnectConnection(Connection connection) {
        // TODO: disconect previous connection
        if (connection == null) {
            return;
        }

        ServerConnection serverConnection = this.serverConnectionFactory.createConnection(connection);
        serverConnection.connect();

        List<DatabaseConnection> databases = new ArrayList<>();
        for (String databaseName : serverConnection.getDatabases()) {
            databases.add(new DatabaseConnection(connection, databaseName));
        }

        this.databaseService.setAll(databases);
    }

    protected void onDatabaseConnect(DatabaseConnection connection) {
        // TODO: disconect previous connection
        if (connection == null) {
            return;
        }

        ServerConnection serverConnection = this.serverConnectionFactory.createConnection(connection);
        serverConnection.connect();

        List<Table> tables = new ArrayList<>();
        for (String tableNames : serverConnection.getTables()) {
            tables.add(new Table(connection, tableNames, new TableChange(tableNames)));
        }
        this.tableService.setAll(tables);
    }

    protected void onTableSelect(Table table) {
        if (table == null) {
            return;
        }

        ServerConnection serverConnection = this.serverConnectionFactory.createConnection(table.getDatabase());

        this.indexService.setAll(
            this.getTransformedIndexes(
                serverConnection.getIndexes(table.getName())
            )
        );
        
        this.columnService.setAll(
            this.getTransformedColumns(
                serverConnection.getColumns(table.getName())
            )
        );
    }

    private Collection<Index> getTransformedIndexes(ObservableList<List<String>> rawIndexes) {
        Map<String, Index> indexes = new LinkedHashMap<>();
        for (List<String> indexValues : rawIndexes) {
            String indexName = indexValues.get(0);
            if (!indexes.containsKey(indexName)) {
                indexes.put(indexName, new Index(indexName, new IndexChange(indexName, new ChangeCommand())));
            }
            Index index = indexes.get(indexName);
            index.addColumn( indexValues.get(1));
        }
        return indexes.values();
    }

    private Collection<Column> getTransformedColumns(ObservableList<List<String>> rawColumns) {
        List<Column> columns = new ArrayList<>();
        for (List<String> columnName : rawColumns) {
            columns.add(
                new Column(
                    columnName.get(0),
                    columnName.get(1),
                    columnName.get(3),
                    columnName.get(2) == "YES" ? true : false,
                    new ColumnChange(columnName.get(0), new ChangeCommand())
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