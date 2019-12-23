package migrator.app.database.column;

import java.util.List;
import java.util.Map;

import migrator.app.migration.model.ColumnProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.adapter.ListAdapter;

public class ColumnStructure {
    protected DatabaseColumnDriver columnDriver;
    protected Adapter<List<ColumnProperty>, List<Map<String, String>>> adapter;

    public ColumnStructure(DatabaseColumnDriver columnDriver, Adapter<ColumnProperty, Map<String, String>> adapter) {
        this.columnDriver = columnDriver;
        this.adapter = new ListAdapter<>(adapter);
    }

    public List<ColumnProperty> getForTable(String tableName) {
        return this.adapter.concretize(
            this.columnDriver.getColumns(tableName)
        );
    }
}