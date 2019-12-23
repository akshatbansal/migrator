package migrator.app.modification;

import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;

public interface ModificationContainer {
    public ModificationCollection<TableProperty> getTableCollection();
    public ModificationCollection<ColumnProperty> getColumnCollection(String table);
    public ModificationCollection<IndexProperty> getIndexCollection(String table);
}