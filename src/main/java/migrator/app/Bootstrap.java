package migrator.app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import migrator.app.code.CodeManager;
import migrator.app.database.DatabaseContainer;
import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.database.column.format.SimpleAppColumnFormat;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.column.ColumnAdapter;
import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.column.service.SimpleColumnService;
import migrator.app.domain.connection.service.ConnectionFactory;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.service.DatabaseFactory;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.index.IndexAdapter;
import migrator.app.domain.index.IndexRepository;
import migrator.app.domain.index.service.IndexActiveState;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.index.service.SimpleIndexService;
import migrator.app.domain.project.service.ProjectFactory;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.TableAdapter;
import migrator.app.domain.table.TableRepository;
import migrator.app.domain.table.service.SimpleTableService;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.app.gui.GuiContainer;
import migrator.app.migration.Migration;
import migrator.app.migration.model.change.ChangeCommandAdapter;
import migrator.app.migration.model.column.ColumnPropertyAdapter;
import migrator.app.migration.model.index.IndexPropertyAdapter;
import migrator.app.migration.model.table.TablePropertyAdapter;
import migrator.app.router.ActiveRoute;
import migrator.app.toast.PermanentToastService;
import migrator.lib.adapter.SimpleJsonListAdapter;
import migrator.lib.hotkyes.HotkeyFactory;
import migrator.lib.hotkyes.SimpleHotkeysService;
import migrator.lib.logger.SystemLogger;
import migrator.lib.repository.UniqueRepository;
import migrator.lib.storage.Storages;
import migrator.lib.uid.Generator;
import migrator.lib.uid.SessionIncrementalGenerator;

public class Bootstrap {
    protected List<Extension> extensions;
    protected Container container;

    public Bootstrap(List<Extension> extensions) {
        ConfigContainer configContainer = new ConfigContainer();
        this.initialize(configContainer);
        
        for (Extension extension : extensions) {
            extension.load(configContainer);
        }

        this.container = new Container(configContainer);
    }

    public Bootstrap(Extension ... extensions) {
        this(Arrays.asList(extensions));
    }

    public Bootstrap() {
        this(new ArrayList<>());
    }

    protected void initialize(ConfigContainer config) {
        Path storageFoldePath = Paths.get(System.getProperty("user.home"), ".migrator");
        if (Files.notExists(storageFoldePath)) {
            storageFoldePath.toFile().mkdirs();
        }
        String session = Long.toString(System.currentTimeMillis());
        Generator idGenerator = new SessionIncrementalGenerator(session);
        
        config.loggerConfig().set(
            new SystemLogger()
        );

        ApplicationColumnFormat basicFormat = new SimpleAppColumnFormat(false, false, false, false);
        ApplicationColumnFormat lengthFormat = new SimpleAppColumnFormat(true, false, false, false);
        ApplicationColumnFormat intFormat = new SimpleAppColumnFormat(true, false, true, true);
        ApplicationColumnFormat decimalFormat = new SimpleAppColumnFormat(true, true, true, false);
        DatabaseContainer databaseContainer = new DatabaseContainer();
        ApplicationColumnFormatCollection appColumnFormatCollection = databaseContainer.getApplicationColumnFormatCollection();
        appColumnFormatCollection.addFormat(
            "boolean",
            basicFormat
        );
        appColumnFormatCollection.addFormat(
            "char",
            lengthFormat
        );
        appColumnFormatCollection.addFormat(
            "date",
            basicFormat
        );
        appColumnFormatCollection.addFormat(
            "datetime",
            basicFormat
        );
        appColumnFormatCollection.addFormat(
            "decimal",
            decimalFormat
        );
        appColumnFormatCollection.addFormat(
            "float",
            decimalFormat
        );
        appColumnFormatCollection.addFormat(
            "integer", 
            intFormat
        );
        appColumnFormatCollection.addFormat(
            "long",
            intFormat
        );
        appColumnFormatCollection.addFormat(
            "string",
            lengthFormat
        );
        appColumnFormatCollection.addFormat(
            "text",
            basicFormat
        );
        appColumnFormatCollection.addFormat(
            "time",
            basicFormat
        );
        appColumnFormatCollection.addFormat(
            "timestamp",
            basicFormat
        );
        
        config.databaseContainerConfig().set(databaseContainer);
        config.guiContainerConfig().set(
            new GuiContainer(databaseContainer)
        );

        config.changeCommandRepositoryConfig().set(
            new UniqueRepository<>()
        );
        config.indexPropertyRepositoryConfig().set(
            new UniqueRepository<>()
        );
        config.columnPropertyRepositoryConfig().set(
            new UniqueRepository<>()
        );
        config.tablePropertyRepositoryConfig().set(
            new UniqueRepository<>()
        );
        config.columnRepositoryConfig().set(
            new ColumnRepository(
                config.columnPropertyRepositoryConfig().get(),
                config.changeCommandRepositoryConfig().get()
            )
        );
        config.indexRepositoryConfig().set(
            new IndexRepository(
                config.indexPropertyRepositoryConfig().get(),
                config.changeCommandRepositoryConfig().get()
            )
        );
        config.tableRepositoryConfig().set(
            new TableRepository(
                config.tablePropertyRepositoryConfig().get(),
                config.changeCommandRepositoryConfig().get()
            )
        );

        config.changeCommandStorageConfig().set(
            Storages.getFileStorage(
                new File(storageFoldePath.toString(), "change_command.json"),
                new SimpleJsonListAdapter<>(
                    new ChangeCommandAdapter()
                )
            )
        );
        config.columnPropertyStorageConfig().set(
            Storages.getFileStorage(
                new File(storageFoldePath.toString(), "column_property.json"),
                new SimpleJsonListAdapter<>(
                    new ColumnPropertyAdapter()
                )
            )
        );
        config.indexPropertyStorageConfig().set(
            Storages.getFileStorage(
                new File(storageFoldePath.toString(), "index_property.json"),
                new SimpleJsonListAdapter<>(
                    new IndexPropertyAdapter(
                        config.columnPropertyRepositoryConfig().get()
                    )
                )
            )
        );
        config.tablePropertyStorageConfig().set(
            Storages.getFileStorage(
                new File(storageFoldePath.toString(), "table_property.json"),
                new SimpleJsonListAdapter<>(
                    new TablePropertyAdapter()
                )
            )
        );
        config.columnStorageConfig().set(
            Storages.getFileStorage(
                new File(storageFoldePath.toString(), "column.json"),
                new SimpleJsonListAdapter<>(
                    new ColumnAdapter(
                        config.columnPropertyRepositoryConfig().get(),
                        config.changeCommandRepositoryConfig().get(),
                        config.guiContainerConfig().get().getColumnFormatCollection()
                    )
                )
            )
        );
        config.indexStorageConfig().set(
            Storages.getFileStorage(
                new File(storageFoldePath.toString(), "index.json"),
                new SimpleJsonListAdapter<>(
                    new IndexAdapter(
                        config.indexPropertyRepositoryConfig().get(),
                        config.changeCommandRepositoryConfig().get()
                    )
                )
            )
        );
        config.tableStorageConfig().set(
            Storages.getFileStorage(
                new File(storageFoldePath.toString(), "table.json"),
                new SimpleJsonListAdapter<>(
                    new TableAdapter(
                        config.tablePropertyRepositoryConfig().get(),
                        config.changeCommandRepositoryConfig().get(),
                        config.columnRepositoryConfig().get(),
                        config.indexRepositoryConfig().get()
                    )
                )
            )
        );

        config.getHoteyFactory().set(
            new HotkeyFactory()
        );
        config.getHoteyService().set(
            new SimpleHotkeysService()
        );

        config.activeRouteConfig().set(
            new ActiveRoute()
        );
        config.migrationConfig().set(
            new Migration(config.getMigrationConfig())
        );
        config.databaseDriverManagerConfig().set(
            new DatabaseDriverManager(config.getDatabaseDriverConfig())
        );
        config.codeManagerConfig().set(
            new CodeManager(config.getCodeConfig())
        );

        config.connectionFactoryConfig().set(
            new ConnectionFactory()
        );
        config.databaseFactoryConfig().set(
            new DatabaseFactory(
                config.connectionFactoryConfig().get()
            )
        );
        config.tableFactoryConfig().set(
            new TableFactory(
                config.columnRepositoryConfig().get(),
                config.indexRepositoryConfig().get(),
                idGenerator
            )
        );
        config.columnFactoryConfig().set(
            new ColumnFactory(
                idGenerator,
                config.guiContainerConfig().get().getColumnFormatCollection()
            )
        );
        config.indexFactoryConfig().set(
            new IndexFactory(
                idGenerator
            )
        );

        config.projectFactoryConfig().set(
            new ProjectFactory(
                config.databaseFactoryConfig().get(),
                idGenerator
            )
        );

        config.connectionServiceConfig().set(
            new ConnectionService()
        );
        config.databaseServiceConfig().set(
            new DatabaseService()
        );
        config.toastServiceConfig().set(
            new PermanentToastService()
        );
        
        config.projectServiceConfig().set(
            new ProjectService(
                config.projectFactoryConfig().get(),
                config.databaseDriverManagerConfig().get(),
                config.toastServiceConfig().get(),
                config.activeRouteConfig().get()
            )
        );
        config.tableActiveStateConfig().set(
            new TableActiveState(
                config.tableRepositoryConfig().get(),
                config.activeRouteConfig().get()
            )
        );
        config.tableServiceConfig().set(
            new SimpleTableService(
                config.tableFactoryConfig().get(),
                config.tableRepositoryConfig().get(),
                config.tableActiveStateConfig().get(),
                config.databaseDriverManagerConfig().get(),
                config.projectServiceConfig().get(),
                config.activeRouteConfig().get()
            )
        );
        config.columnActiveStateConfig().set(
            new ColumnActiveState(
                config.columnRepositoryConfig().get(), 
                config.tableActiveStateConfig().get(),
                config.activeRouteConfig().get(),
                config.projectServiceConfig().get()
            )
        );
        config.columnServiceConfig().set(
            new SimpleColumnService(
                config.columnRepositoryConfig().get(),
                config.columnActiveStateConfig().get(),   
                config.columnFactoryConfig().get(),
                config.tableActiveStateConfig().get(),
                config.projectServiceConfig().get(),
                config.databaseDriverManagerConfig().get()
            )
        );
        config.indexActiveStateConfig().set(
            new IndexActiveState(
                config.indexRepositoryConfig().get(),
                config.indexPropertyRepositoryConfig().get(),
                config.changeCommandRepositoryConfig().get(),
                config.tableActiveStateConfig().get(),
                config.activeRouteConfig().get(),
                config.projectServiceConfig().get()
            )
        );
        config.indexServiceConfig().set(
            new SimpleIndexService(
                config.indexFactoryConfig().get(),
                config.indexRepositoryConfig().get(),
                config.indexActiveStateConfig().get(),
                config.tableActiveStateConfig().get(),
                config.projectServiceConfig().get(),
                config.databaseDriverManagerConfig().get()
            )
        );
    }

    public Container getContainer() {
        return this.container;
    }
}