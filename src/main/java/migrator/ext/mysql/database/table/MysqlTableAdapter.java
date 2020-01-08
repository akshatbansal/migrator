package migrator.ext.mysql.database.table;

import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableProperty;
import migrator.lib.adapter.Adapter;

public class MysqlTableAdapter implements Adapter<String, TableProperty> {
    @Override
    public String concretize(TableProperty item) {
        if (item == null) {
            return "";
        }
        return item.getName();
    }

    @Override
    public TableProperty generalize(String item) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        return new SimpleTableProperty("", item);
    }
} 