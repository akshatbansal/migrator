package migrator.app.database.table;

import java.util.List;

import migrator.app.migration.model.TableProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.adapter.ListAdapter;

public class TableStructure {
    protected DatabaseTableDriver mysqlTableDriver;
    protected Adapter<List<String>, List<TableProperty>> adapter;

    public TableStructure(DatabaseTableDriver mysqlDatabaseTableDriver, Adapter<String, TableProperty> itemAdapter) {
        this.mysqlTableDriver = mysqlDatabaseTableDriver;
        this.adapter = new ListAdapter<>(itemAdapter);
    }

    public List<TableProperty> get() {
        return this.adapter.generalize(
            this.mysqlTableDriver.getTables()
        );
    }
}