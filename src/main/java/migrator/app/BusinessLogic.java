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
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.migration.model.TableChange;

public class BusinessLogic {
    protected Container container;

    public BusinessLogic(Container container) {
        this.container = container;

        this.container.getTableService()
            .getSelected()
            .addListener((ObservableValue<? extends Table> observable, Table oldValue, Table newValue) -> {
                this.onTableSelect(newValue);
            });

        this.container.getProjectService()
            .getOpened()
            .addListener((ObservableValue<? extends Project> obs, Project oldValue, Project newValue) -> {
                this.onProjectOpen(newValue);
            });
    }

    protected void onTableSelect(Table table) {
        if (table == null) {
            return;
        }

        DatabaseDriver databaseDriver = this.container.getDatabaseDriverManager()
            .createDriver(table.getProject().getDatabase());

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

    public void onProjectOpen(Project project) {
        // TODO: disconect previous connection
        if (project == null) {
            return;
        }

        DatabaseDriver databaseDriver  = this.container.getDatabaseDriverManager()
            .createDriver(project.getDatabase());
        databaseDriver.connect();

        ChangeService changeService = this.container.getChangeService();
        TableFactory tableFactory = this.container.getTableFactory();

        List<Table> tables = new ArrayList<>();
        for (String tableName : databaseDriver.getTables()) {
            TableChange tableChange = changeService.getTableChange(project.getName(), tableName);
            if (tableChange == null) {
                tableChange = changeService.getTableChangeFactory()
                    .createNotChanged(tableName);
                changeService.addTableChange(project.getName(), tableChange);
            }
            Table table = tableFactory.create(project, tableName, tableChange);
            tables.add(table);
        }
        List<TableChange> createdTableChanges = changeService.getCreatedTableChanges(project.getName());
        for (TableChange tableChange : createdTableChanges) {
            tables.add(
                tableFactory.create(project, tableChange.getOriginalName(), tableChange)
            );
        }
        this.container.getTableService()
            .setAll(tables);
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