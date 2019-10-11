package migrator.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import migrator.app.code.CodeManager;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.column.service.ColumnRepository;
import migrator.app.domain.column.service.SimpleColumnService;
import migrator.app.domain.connection.service.ConnectionFactory;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.service.DatabaseFactory;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.index.service.IndexActiveState;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.index.service.IndexRepository;
import migrator.app.domain.index.service.SimpleIndexService;
import migrator.app.domain.project.service.ProjectFactory;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.service.SimpleTableService;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.domain.table.service.TableRepository;
import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.app.migration.Migration;
import migrator.app.router.ActiveRoute;
import migrator.app.toast.AutohideToastService;

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
        ColumnRepository columnRepository = new ColumnRepository();
        IndexRepository indexRepository = new IndexRepository();
        
        config.tableRepositoryConfig().set(
            new TableRepository()
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
                columnRepository,
                indexRepository
            )
        );
        config.columnFactoryConfig().set(
            new ColumnFactory()
        );
        config.indexFactoryConfig().set(
            new IndexFactory()
        );

        config.projectFactoryConfig().set(
            new ProjectFactory(
                config.databaseFactoryConfig().get()
            )
        );

        config.connectionServiceConfig().set(
            new ConnectionService()
        );
        config.databaseServiceConfig().set(
            new DatabaseService()
        );
        config.toastServiceConfig().set(
            new AutohideToastService(5000)
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
                config.projectServiceConfig().get(),
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
                columnRepository, 
                config.tableActiveStateConfig().get()
            )
        );
        config.columnServiceConfig().set(
            new SimpleColumnService(
                columnRepository,
                config.columnActiveStateConfig().get(),   
                config.columnFactoryConfig().get(),
                config.tableActiveStateConfig().get(),
                config.databaseDriverManagerConfig().get()
            )
        );
        config.indexActiveStateConfig().set(
            new IndexActiveState(
                indexRepository,
                config.tableActiveStateConfig().get()
            )
        );
        config.indexServiceConfig().set(
            new SimpleIndexService(
                config.indexFactoryConfig().get(),
                indexRepository,
                config.indexActiveStateConfig().get(),
                config.tableActiveStateConfig().get(),
                config.databaseDriverManagerConfig().get()
            )
        );
    }

    public Container getContainer() {
        return this.container;
    }
}