package migrator.app.database;

import java.util.List;
import java.util.Map;

import migrator.app.migration.model.ColumnProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.adapter.ListAdapter;

public class ColumnStructure {
    protected DatabaseColumnDriver columnDriver;
    protected Adapter<List<Map<String, String>>, List<ColumnProperty>> adapter;

    public ColumnStructure(DatabaseColumnDriver columnDriver, Adapter<Map<String, String>, ColumnProperty> adapter) {
        this.columnDriver = columnDriver;
        this.adapter = new ListAdapter<>(adapter);
    }

    public List<ColumnProperty> getForTable(String tableName) {
        return this.adapter.generalize(
            this.columnDriver.getColumns(tableName)
        );
    }
}