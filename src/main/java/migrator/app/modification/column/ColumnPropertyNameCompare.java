package migrator.app.modification.column;

import migrator.app.migration.model.ColumnProperty;
import migrator.lib.diff.DiffCompare;

public class ColumnPropertyNameCompare implements DiffCompare<ColumnProperty> {
    @Override
    public Boolean compare(ColumnProperty a, ColumnProperty b) {
        if (a == null || b == null) {
            return false;
        }
        return a.getName().equals(b.getName());
    }
}