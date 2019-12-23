package migrator.app.modification.index;

import migrator.app.migration.model.IndexProperty;
import migrator.lib.diff.DiffCompare;

public class IndexPropertyNameCompare implements DiffCompare<IndexProperty> {
    @Override
    public Boolean compare(IndexProperty a, IndexProperty b) {
        if (a == null || b == null) {
            return false;
        }
        return a.getName().equals(b.getName());
    }
}