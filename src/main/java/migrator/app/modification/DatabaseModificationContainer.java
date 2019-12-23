package migrator.app.modification;

import java.util.Hashtable;
import java.util.Map;

import migrator.app.database.DatabaseStructure;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;
import migrator.app.modification.column.ColumnModificationFactory;
import migrator.app.modification.column.ColumnPropertyNameCompare;
import migrator.app.modification.index.IndexModificationFactory;
import migrator.app.modification.index.IndexPropertyNameCompare;
import migrator.app.modification.table.TableModificationFactory;
import migrator.app.modification.table.TablePropertyNameCompare;

public class DatabaseModificationContainer implements ModificationContainer {
    protected DatabaseStructure databaseStructure;
    protected ModificationCollection<TableProperty> tableCollection;
    protected Map<String, ModificationCollection<ColumnProperty>> columnCollections;
    protected Map<String, ModificationCollection<IndexProperty>> indexCollections;

    public DatabaseModificationContainer(DatabaseStructure databaseStructure) {
        this.databaseStructure = databaseStructure;
        this.tableCollection = new DatabaseModifications<>(
            databaseStructure.getTables(),
            new TableModificationFactory(),
            new TablePropertyNameCompare()
        );

        this.columnCollections = new Hashtable<>();
        this.indexCollections = new Hashtable<>();
    }

    @Override
    public ModificationCollection<ColumnProperty> getColumnCollection(String table) {
        if (!this.columnCollections.containsKey(table)) {
            this.columnCollections.put(
                table, 
                new DatabaseModifications<>(
                    this.databaseStructure.getColumns(table),
                    new ColumnModificationFactory(),
                    new ColumnPropertyNameCompare()
                )
            );
        }
        return this.columnCollections.get(table);
    }

    @Override
    public ModificationCollection<IndexProperty> getIndexCollection(String table) {
        if (!this.indexCollections.containsKey(table)) {
            this.indexCollections.put(
                table, 
                new DatabaseModifications<>(
                    this.databaseStructure.getIndexes(table),
                    new IndexModificationFactory(),
                    new IndexPropertyNameCompare()
                )
            );
        }
        return this.indexCollections.get(table);
    }

    @Override
    public ModificationCollection<TableProperty> getTableCollection() {
        return this.tableCollection;
    }
}