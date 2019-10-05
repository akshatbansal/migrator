package migrator.app;

import java.util.List;

import migrator.app.code.CodeManager;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.change.service.ChangeService;
import migrator.app.domain.change.service.TableChangeFactory;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.table.service.ColumnFactory;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.IndexFactory;
import migrator.app.domain.table.service.IndexService;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.domain.table.service.TableService;
import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.app.migration.Migration;
import migrator.app.router.ActiveRoute;
import migrator.javafx.breadcrumps.BreadcrumpsService;

public class Bootstrap {
    protected List<Extension> extensions;
    protected Container container;

    public Bootstrap(
        List<Extension> extensions
    ) {
        ConfigContainer configContainer = new ConfigContainer();
        this.initialize(configContainer);
        
        for (Extension extension : extensions) {
            extension.load(configContainer);
        }

        this.container = new Container(configContainer);
    }

    protected void initialize(ConfigContainer config) {
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

        config.tableChangeFactoryConfig().set(
            new TableChangeFactory()
        );
        config.tableFactoryConfig().set(
            new TableFactory(
                config.tableChangeFactoryConfig().get()
            )
        );
        config.columnFactoryConfig().set(
            new ColumnFactory()
        );
        config.indexFactoryConfig().set(
            new IndexFactory()
        );

        config.changeServiceConfig().set(
            new ChangeService(
                config.tableChangeFactoryConfig().get()
            )
        );
        config.connectionServiceConfig().set(
            new ConnectionService()
        );
        config.databaseServiceConfig().set(
            new DatabaseService()
        );
        config.tableServiceConfig().set(
            new TableService(
                config.changeServiceConfig().get(),
                config.tableFactoryConfig().get()
            )
        );
        config.columnServiceConfig().set(
            new ColumnService(
                config.columnFactoryConfig().get(),
                config.changeServiceConfig().get(), 
                config.tableServiceConfig().get().getSelected()
            )
        );
        config.indexServiceConfig().set(
            new IndexService(
                config.indexFactoryConfig().get()
            )
        );
        config.breadcrumpsServiceConfig().set(
            new BreadcrumpsService()
        );
    }

    public Container getContainer() {
        return this.container;
    }
}