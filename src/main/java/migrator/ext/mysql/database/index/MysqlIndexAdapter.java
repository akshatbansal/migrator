package migrator.ext.mysql.database.index;

import java.util.ArrayList;
import java.util.List;

import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.lib.adapter.Adapter;

public class MysqlIndexAdapter implements Adapter<List<String>, IndexProperty> {
    protected List<ColumnProperty> columns;

    public MysqlIndexAdapter(List<ColumnProperty> columns) {
        this.columns = columns;
    }

    @Override
    public List<String> concretize(IndexProperty item) {
        if (item == null) {
            return null;
        }
        List<String> result = new ArrayList<>();

        result.add(item.getName());
        for (ColumnProperty column : item.columnsProperty()) {
            result.add(column.getName());
        }

        return result;
    }

    @Override
    public IndexProperty generalize(List<String> item) {
        if (item == null) {
            return null;
        }
        if (item.isEmpty()) {
            return null;
        }
        List<ColumnProperty> columnsForIndex = new ArrayList<>();
        for (int i = 1; i < item.size(); ++i) {
            for (ColumnProperty columnProperty : this.columns) {
                if (!columnProperty.getName().equals(item.get(i))) {
                    continue;
                }
                columnsForIndex.add(columnProperty);
            }
        }
        return new SimpleIndexProperty("", item.get(0), columnsForIndex);
    }
} 