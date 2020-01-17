package migrator.app.domain.table;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.index.IndexContainer;
import migrator.app.domain.modification.ModificationContainer;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.SimpleTableStore;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.migration.model.TableProperty;
import migrator.app.migration.model.table.TablePropertyAdapter;
import migrator.app.service.SimpleStore;
import migrator.lib.adapter.SimpleJsonListAdapter;
import migrator.lib.repository.Repository;
import migrator.lib.repository.UniqueRepository;
import migrator.lib.storage.Storage;
import migrator.lib.storage.Storages;
import migrator.lib.uid.Generator;

public class TableContainer {
    private TableFactory tableFactoryVaue;
    private SimpleStore<Table> tableStoreValue;
    private TableRepository tableRepositoryValue;
    private Storage<Collection<Table>> tableStorageValue;

    private UniqueRepository<TableProperty> tablePropertyRepositoryValue;
    private Storage<Collection<TableProperty>> tablePropertyStorageValue;

    public TableContainer(
        Generator generator,
        Path storagePath,
        ModificationContainer modificationContainer,
        ColumnContainer columnContainer,
        IndexContainer indexContainer
    ) {
        this.tablePropertyRepositoryValue = new UniqueRepository<>();
        this.tablePropertyStorageValue = Storages.getFileStorage(
            new File(storagePath.toString(), "table_property.json"),
            new SimpleJsonListAdapter<>(
                new TablePropertyAdapter()
            )
        );

        this.tableStoreValue = new SimpleTableStore();
        this.tableFactoryVaue = new TableFactory(
            columnContainer.columnRepository(),
            indexContainer.indexRepository(),
            generator
        );
        this.tableRepositoryValue = new TableRepository(
            this.tablePropertyRepositoryValue, modificationContainer.repository()
        );
        this.tableStorageValue = Storages.getFileStorage(
            new File(storagePath.toString(), "table.json"),
            new SimpleJsonListAdapter<>(
                new TableAdapter(
                    this.tablePropertyRepositoryValue,
                    modificationContainer.repository(), columnContainer.columnRepository(), indexContainer.indexRepository()
                )
            )
        );
    }

    public TableFactory tableFactory() {
        return this.tableFactoryVaue;
    }

    public SimpleStore<Table> tableStore() {
        return this.tableStoreValue;
    }

    public TableRepository tableRepository() {
        return this.tableRepositoryValue;
    }

    public Storage<Collection<TableProperty>> tablePropertyStorage() {
        return this.tablePropertyStorageValue;
    }

    public Storage<Collection<Table>> tableStorage() {
        return this.tableStorageValue;
    }

    public Repository<TableProperty> tablePropertyRepository() {
        return this.tablePropertyRepositoryValue;
    }
}