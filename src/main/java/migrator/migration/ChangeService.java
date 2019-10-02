package migrator.migration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeService {
    protected TableChangeFactory tableChangeFactory;
    protected Map<String, List<TableChange>> tables;

    public ChangeService(TableChangeFactory tableChangeFactory) {
        this.tableChangeFactory = tableChangeFactory;
        this.tables = new HashMap<>();
    }

    // public ChangeService(TableChange ... changes) {
    //     this.list = FXCollections.observableArrayList(changes);
    // }

    // public ObservableList<TableChange> getChanges() {
    //     return this.list;
    // }

    public void addTableChange(String databaseName, TableChange tableChange) {
        if (!this.tables.containsKey(databaseName)) {
            this.tables.put(databaseName, new ArrayList<>());
        }
        this.tables.get(databaseName).add(tableChange);
    }

    // public void addColumnChange(String table, ColumnChange columnChange) {
    //     TableChange tableChange = this.getOrCreateTable(table);
    //     tableChange.addColumnChange(columnChange);
    // }

    // public TableChange getOrCreateTable(String table) {
    //     TableChange tableChange = this.getTable(table);
    //     if (tableChange == null) {
    //         tableChange = new TableChange(table);
    //         this.addTableChange(tableChange);
    //     }
    //     return tableChange;
    // }

    // public TableChange getTable(String table) {
    //     for (TableChange tableChange : this.list) {
    //         if (tableChange.getName() == table) {
    //             return tableChange;
    //         }
    //     }
    //     return null;
    // }

    public List<TableChange> getTables(String databaseName) {
        if (!this.tables.containsKey(databaseName)) {
            this.tables.put(databaseName, new ArrayList<>());
        }
        return this.tables.get(databaseName);
    }

    public TableChange getTableChange(String databaseName, String tableName) {
        List<TableChange> tables = this.getTables(databaseName);
        for (TableChange tableChange : tables) {
            if (tableName.equals(tableChange.getOriginalName())) {
                return tableChange;
            }
        }
        return null;
    }

    public TableChangeFactory getTableChangeFactory() {
        return this.tableChangeFactory;
    }

    public List<TableChange> getCreatedTableChanges(String databaseName) {
        List<TableChange> created = new ArrayList<>();
        List<TableChange> tables = this.getTables(databaseName);
        for (TableChange tableChange : tables) {
            if (!tableChange.getCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            created.add(tableChange);
        }
        return created;
    }
}