package migrator.ext.mysql.table;

import java.util.List;

import migrator.app.database.DatabaseTableDriver;
import migrator.app.migration.model.TableProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.adapter.ListAdapter;

public class MysqlTableStructure {
    protected DatabaseTableDriver mysqlTableDriver;
    protected Adapter<List<String>, List<TableProperty>> adapter;

    public MysqlTableStructure(DatabaseTableDriver mysqlDatabaseTableDriver) {
        this.mysqlTableDriver = mysqlDatabaseTableDriver;
        this.adapter = new ListAdapter<>(
            new MysqlTableAdapter()
        );
    }

    public List<TableProperty> get() {
        return this.adapter.generalize(
            this.mysqlTableDriver.getTables()
        );
    }
}