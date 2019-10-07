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
import migrator.app.domain.change.service.ChangeService;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.migration.model.TableChange;

public class BusinessLogic {
    protected Container container;

    public BusinessLogic(Container container) {
        this.container = container;

        this.container.getConnectionService()
            .getConnected()
            .addListener((ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) -> {
                this.onConnectConnection(newValue);
            });

        this.container.getDatabaseService()
            .getConnected()
            .addListener((ObservableValue<? extends DatabaseConnection> observable, DatabaseConnection oldValue, DatabaseConnection newValue) -> {
                this.onDatabaseConnect(newValue);
            });

        this.container.getTableService()
            .getSelected()
            .addListener((ObservableValue<? extends Table> observable, Table oldValue, Table newValue) -> {
                this.onTableSelect(newValue);
            });
    }

    protected void onConnectConnection(Connection connection) {
        // TODO: disconect previous connection
        if (connection == null) {
            return;
        }

        DatabaseDriver databaseDriver  = this.container.getDatabaseDriverManager()
            .createDriver(connection);
        databaseDriver.connect();

        List<DatabaseConnection> databases = new ArrayList<>();
        for (String databaseName : databaseDriver.getDatabases()) {
            databases.add(new DatabaseConnection(connection, databaseName));
        }

        this.container.getDatabaseService().setAll(databases);
    }

    protected void onDatabaseConnect(DatabaseConnection connection) {
        // TODO: disconect previous connection
        if (connection == null) {
            return;
        }

        DatabaseDriver databaseDriver  = this.container.getDatabaseDriverManager()
            .createDriver(connection);
        databaseDriver.connect();

        ChangeService changeService = this.container.getChangeService();
        TableFactory tableFactory = this.container.getTableFactory();

        String databaseDotString = connection.getConnection().getName() + "." + connection.getDatabase();
        List<Table> tables = new ArrayList<>();
        for (String tableName : databaseDriver.getTables()) {
            TableChange tableChange = changeService.getTableChange(databaseDotString, tableName);
            if (tableChange == null) {
                tableChange = changeService.getTableChangeFactory()
                    .createNotChanged(tableName);
                changeService.addTableChange(databaseDotString, tableChange);
            }
            Table table = tableFactory.create(connection, tableName, tableChange);
            tables.add(table);
        }
        List<TableChange> createdTableChanges = changeService.getCreatedTableChanges(databaseDotString);
        for (TableChange tableChange : createdTableChanges) {
            tables.add(
                tableFactory.create(connection, tableChange.getOriginalName(), tableChange)
            );
        }
        this.container.getTableService()
            .setAll(tables);
    }

    protected void onTableSelect(Table table) {
        System.out.println("on table");
        if (table == null) {
            return;
        }

        DatabaseDriver databaseDriver = this.container.getDatabaseDriverManager()
            .createDriver(table.getDatabase());

        this.container.getIndexService()
            .setAll(
                this.getTransformedIndexes(
                    databaseDriver.getIndexes(table.getOriginalName())
                )
            );
        
        this.container.getColumnService()
            .setAll(
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
                this.container.getIndexFactory()
                    .createNotChanged(entry.getKey(), entry.getValue())
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
                this.container.getColumnFactory()
                    .createNotChanged(
                        columnName.get(0),
                        columnName.get(1),
                        defaultValue,
                        columnName.get(2) == "YES" ? true : false
                    )
            );
        }
        return columns;
    }
}