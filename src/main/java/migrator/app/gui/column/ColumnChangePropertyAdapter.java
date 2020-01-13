package migrator.app.gui.column;

import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.adapter.Adapter;

public class ColumnChangePropertyAdapter implements Adapter<ColumnProperty, Column> {
    @Override
    public ColumnProperty concretize(Column item) {
        return item.getModification();
    }

    @Override
    public Column generalize(ColumnProperty item) {
        return null;
    }
}