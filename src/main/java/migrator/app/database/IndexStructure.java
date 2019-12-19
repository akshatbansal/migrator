package migrator.app.database;

import java.util.List;

import migrator.app.migration.model.IndexProperty;
import migrator.lib.adapter.Adapter;
import migrator.lib.adapter.ListAdapter;

public class IndexStructure {
    protected DatabaseIndexDriver indexDriver;
    protected Adapter<List<List<String>>, List<IndexProperty>> adapter;

    public IndexStructure(DatabaseIndexDriver indexDriver, Adapter<List<String>, IndexProperty> itemAdapter) {
        this.indexDriver = indexDriver;
        this.adapter = new ListAdapter<>(itemAdapter);
    }

    public List<IndexProperty> getForTable(String tableName) {
        return this.adapter.generalize(
            this.indexDriver.getIndexes(tableName)
        );
    }
}