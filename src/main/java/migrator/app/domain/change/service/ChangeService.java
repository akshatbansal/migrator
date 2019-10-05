package migrator.app.domain.change.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.TableChange;

public class ChangeService {
    protected TableChangeFactory tableChangeFactory;
    protected Map<String, List<TableChange>> tables;

    public ChangeService(TableChangeFactory tableChangeFactory) {
        this.tableChangeFactory = tableChangeFactory;
        this.tables = new HashMap<>();
    }

    public void addTableChange(String databaseName, TableChange tableChange) {
        if (!this.tables.containsKey(databaseName)) {
            this.tables.put(databaseName, new ArrayList<>());
        }
        this.tables.get(databaseName).add(tableChange);
    }

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