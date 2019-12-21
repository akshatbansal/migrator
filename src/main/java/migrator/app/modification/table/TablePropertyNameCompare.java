package migrator.app.modification.table;

import migrator.app.migration.model.TableProperty;
import migrator.lib.diff.DiffCompare;

public class TablePropertyNameCompare implements DiffCompare<TableProperty> {
    @Override
    public Boolean compare(TableProperty a, TableProperty b) {
        if (a == null || b == null) {
            return false;
        }
        return a.getName().equals(b.getName());
    }
}