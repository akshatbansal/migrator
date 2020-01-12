package migrator.app.boot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import migrator.app.ProxyLogger;
import migrator.app.code.CodeContainer;
import migrator.app.config.ConfigContainer;
import migrator.app.database.DatabaseContainer;
import migrator.app.domain.column.ColumnContainer;
import migrator.app.domain.connection.service.ConnectionFactory;
import migrator.app.domain.database.service.DatabaseFactory;
import migrator.app.domain.index.IndexContainer;
import migrator.app.domain.modification.ModificationContainer;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.model.ProjectAdapter;
import migrator.app.domain.project.service.ProjectFactory;
import migrator.app.domain.project.service.ProjectStore;
import migrator.app.domain.project.service.SimpleProjectStore;
import migrator.app.domain.table.TableContainer;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.app.migration.MigrationContainer;
import migrator.lib.adapter.SimpleJsonListAdapter;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.logger.SystemLogger;
import migrator.lib.storage.Storage;
import migrator.lib.storage.Storages;
import migrator.lib.uid.Generator;
import migrator.lib.uid.SessionIncrementalGenerator;

public class Container {
    private Generator generatorValue;

    private ModificationContainer modificationContainerValue;

    private ProjectFactory projectFactoryValue;
    private ProjectStore projectStoreValue;
    private Storage<Collection<Project>> projectStorageValue;

    private TableContainer tableContainerValue;
    private ColumnContainer columnContainerValue;
    private IndexContainer indexContainerValue;

    private DatabaseContainer databaseContainerValue;
    private EventDispatcher dispatcherValue;
    private MigrationContainer migrationContainerValue;
    private CodeContainer codeContainerValue;
    private ProxyLogger loggerValue;
    private ConfigContainer configContainerValue;

    public Container() {
        Path storageFoldePath = Paths.get(System.getProperty("user.home"), ".migrator");
        if (Files.notExists(storageFoldePath)) {
            storageFoldePath.toFile().mkdirs();
        }
        String session = Long.toString(System.currentTimeMillis());
        this.generatorValue = new SessionIncrementalGenerator(session);

        this.configContainerValue = new ConfigContainer();
        this.modificationContainerValue = new ModificationContainer(storageFoldePath);
        this.dispatcherValue = new EventDispatcher();
        this.databaseContainerValue = new DatabaseContainer();
        this.migrationContainerValue = new MigrationContainer();
        this.codeContainerValue = new CodeContainer();
        this.loggerValue = new ProxyLogger(
            new SystemLogger()
        );

        this.columnContainerValue = new ColumnContainer(
            generatorValue,
            new ColumnFormatCollection(
                this.databaseContainerValue.getApplicationColumnFormatCollection().getObservable()
            ),
            this.modificationContainerValue.repository(),
            storageFoldePath
        );

        this.indexContainerValue = new IndexContainer(
            generatorValue,
            storageFoldePath,
            this.columnContainer().columnPropertyRepository(),
            this.modificationContainer().repository()
        );
        this.tableContainerValue = new TableContainer(
            generatorValue,
            storageFoldePath,
            this.modificationContainer(),
            this.columnContainer(),
            this.indexContainer()
        );

        this.projectStoreValue = new SimpleProjectStore(this.databaseContainerValue);
        this.projectFactoryValue = new ProjectFactory(
            new DatabaseFactory(
                new ConnectionFactory()
            ),
            generatorValue
        );
        this.projectStorageValue = Storages.getFileStorage(
            new File(storageFoldePath.toString(), "project.json"),
            new SimpleJsonListAdapter<>(
                new ProjectAdapter()
            )
        );
    }

    public ProjectStore projectStore() {
        return this.projectStoreValue;
    }

    public DatabaseContainer databaseContainer() {
        return this.databaseContainerValue;
    }

    public EventDispatcher dispatcher() {
        return this.dispatcherValue;
    }

    public MigrationContainer migrationContainer() {
        return this.migrationContainerValue;
    }

    public CodeContainer codeContainer() {
        return this.codeContainerValue;
    }

    public ProxyLogger logger() {
        return this.loggerValue;
    }

    public ConfigContainer configContainer() {
        return this.configContainerValue;
    }

    public ProjectFactory projectFactory() {
        return this.projectFactoryValue;
    }

    public Storage<Collection<Project>> projectStorage() {
        return this.projectStorageValue;
    }

    public TableContainer tableContainer() {
        return this.tableContainerValue;
    }

    public ModificationContainer modificationContainer() {
        return this.modificationContainerValue;
    }

    public ColumnContainer columnContainer() {
        return this.columnContainerValue;
    }

    public IndexContainer indexContainer() {
        return this.indexContainerValue;
    }
}