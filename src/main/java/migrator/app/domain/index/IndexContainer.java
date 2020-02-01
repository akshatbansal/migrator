package migrator.app.domain.index;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.index.service.IndexStore;
import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.index.IndexPropertyAdapter;
import migrator.app.service.SelectableStore;
import migrator.lib.adapter.SimpleJsonListAdapter;
import migrator.lib.filesystem.Filesystem;
import migrator.lib.repository.UniqueRepository;
import migrator.lib.storage.AdapterStorage;
import migrator.lib.storage.SimpleFileStorage;
import migrator.lib.storage.Storage;
import migrator.lib.uid.Generator;

public class IndexContainer {
    private SelectableStore<Index> indexStoreValue;
    private IndexRepository indexRepositoryValue;
    private Storage<Collection<Index>> indexStorageValue;
    private IndexFactory indexFactoryValue;

    private UniqueRepository<IndexProperty> indexPropertyRepositoryValue;
    private Storage<Collection<IndexProperty>> indexPropertyStorageValue;

    public IndexContainer(
        Generator generator,
        Path storagePath,
        UniqueRepository<ColumnProperty> columnPropertyRepo,
        UniqueRepository<ChangeCommand> changeCommandRepo,
        Filesystem filesystem
    ) {
        this.indexPropertyRepositoryValue = new UniqueRepository<>();
        this.indexPropertyStorageValue = new AdapterStorage<>(
            new SimpleFileStorage(
                filesystem,
                new File(storagePath.toString(), "index_property.json")
            ),
            new SimpleJsonListAdapter<>(
                new IndexPropertyAdapter(columnPropertyRepo)
            )
        );

        this.indexFactoryValue = new IndexFactory(generator);
        this.indexStoreValue = new IndexStore();
        this.indexStorageValue = new AdapterStorage<>(
            new SimpleFileStorage(
                filesystem,
                new File(storagePath.toString(), "index.json")
            ),
            new SimpleJsonListAdapter<>(
                new IndexAdapter(this.indexPropertyRepositoryValue, changeCommandRepo)
            )
        );
        this.indexRepositoryValue = new IndexRepository(indexPropertyRepositoryValue, changeCommandRepo);
        
    }

    public SelectableStore<Index> indexStore() {
        return this.indexStoreValue;
    }

    public IndexRepository indexRepository() {
        return this.indexRepositoryValue;
    }

    public Storage<Collection<Index>> indexStorage() {
        return this.indexStorageValue;
    }

    public IndexFactory indexFactory() {
        return this.indexFactoryValue;
    }

    public UniqueRepository<IndexProperty> indexPropertyRepository() {
        return this.indexPropertyRepositoryValue;
    }

    public Storage<Collection<IndexProperty>> indexPropertyStorage() {
        return this.indexPropertyStorageValue;
    }
}