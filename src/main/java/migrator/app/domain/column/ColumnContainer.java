package migrator.app.domain.column;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.column.service.SimpleColumnStore;
import migrator.app.domain.table.model.Column;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.column.ColumnPropertyAdapter;
import migrator.app.service.SimpleStore;
import migrator.lib.adapter.SimpleJsonListAdapter;
import migrator.lib.filesystem.Filesystem;
import migrator.lib.repository.UniqueRepository;
import migrator.lib.storage.AdapterStorage;
import migrator.lib.storage.SimpleFileStorage;
import migrator.lib.storage.Storage;
import migrator.lib.uid.Generator;

public class ColumnContainer {
    private SimpleStore<Column> columnStoreValue;
    private UniqueRepository<ColumnProperty> columnPropertyRepositoryValue;
    private ColumnRepository columnRepositoryValue;
    private ColumnFactory columnFactoryValue;
    private Storage<Collection<Column>> columnStorageValue;
    private Storage<Collection<ColumnProperty>> columnPropertyStorageValue;

    public ColumnContainer(
        Generator generator,
        ColumnFormatCollection columnFormatCollection,
        UniqueRepository<ChangeCommand> changeCommandRepo,
        Path storagePath,
        Filesystem filesystem
    ) {
        this.columnFactoryValue = new ColumnFactory(generator, columnFormatCollection);
        this.columnStoreValue = new SimpleColumnStore();
        this.columnPropertyRepositoryValue = new UniqueRepository<>();
        this.columnRepositoryValue = new ColumnRepository(columnPropertyRepositoryValue, changeCommandRepo);
        this.columnStorageValue = new AdapterStorage<>(
            new SimpleFileStorage(
                filesystem,
                new File(storagePath.toString(), "column.json")
            ),
            new SimpleJsonListAdapter<>(
                new ColumnAdapter(this.columnPropertyRepositoryValue, changeCommandRepo, columnFormatCollection)
            )
        );
        this.columnPropertyStorageValue = new AdapterStorage<>(
            new SimpleFileStorage(
                filesystem,
                new File(storagePath.toString(), "column_property.json")
            ),
            new SimpleJsonListAdapter<>(
                new ColumnPropertyAdapter()
            )
        );
    }

    public SimpleStore<Column> columnStore() {
        return this.columnStoreValue;
    }

    public ColumnRepository columnRepository() {
        return this.columnRepositoryValue;
    }

    public UniqueRepository<ColumnProperty> columnPropertyRepository() {
        return this.columnPropertyRepositoryValue;
    }

    public ColumnFactory columnFactory() {
        return this.columnFactoryValue;
    }

    public Storage<Collection<Column>> columnStorage() {
        return this.columnStorageValue;
    }

    public Storage<Collection<ColumnProperty>> columnPropertyStorage() {
        return this.columnPropertyStorageValue;
    }
}