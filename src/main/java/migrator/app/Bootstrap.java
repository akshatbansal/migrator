package migrator.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import migrator.app.code.CodeManager;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.database.format.ColumnFormat;
import migrator.app.database.format.ColumnFormatManager;
import migrator.app.database.format.SimpleColumnFormat;
import migrator.app.database.format.columns.BooleanFormat;
import migrator.app.database.format.columns.CharFormat;
import migrator.app.database.format.columns.DateFormat;
import migrator.app.database.format.columns.DatetimeFormat;
import migrator.app.database.format.columns.DecimalFormat;
import migrator.app.database.format.columns.FloatFormat;
import migrator.app.database.format.columns.IntegerFormat;
import migrator.app.database.format.columns.LongFormat;
import migrator.app.database.format.columns.StringFormat;
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
import migrator.app.toast.PermanentToastService;
import migrator.lib.config.MapConfig;
import migrator.lib.hotkyes.HotkeyFactory;
import migrator.lib.hotkyes.SimpleHotkeysService;
import migrator.lib.logger.SystemLogger;
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
        MapConfig<ColumnFormat>  columnFormatsConfig = config.getColumnFormatConfig();
        columnFormatsConfig.add("boolean", new BooleanFormat());
        columnFormatsConfig.add("char", new CharFormat());
        columnFormatsConfig.add("date", new DateFormat());
        columnFormatsConfig.add("datetime", new DatetimeFormat());
        columnFormatsConfig.add("decimal", new DecimalFormat());
        columnFormatsConfig.add("float", new FloatFormat());
        columnFormatsConfig.add("integer", new IntegerFormat());
        columnFormatsConfig.add("long", new LongFormat());
        columnFormatsConfig.add("string", new StringFormat());
        columnFormatsConfig.add("text", new SimpleColumnFormat("text"));
        columnFormatsConfig.add("time", new SimpleColumnFormat("time"));
        columnFormatsConfig.add("timestamp", new SimpleColumnFormat("timestamp"));

        String session = Long.toString(System.currentTimeMillis());
        Generator idGenerator = new SessionIncrementalGenerator(session);
        
        config.loggerConfig().set(
            new SystemLogger()
        );
        config.columnRepositoryConfig().set(
            new ColumnRepository()
        );
        config.indexRepositoryConfig().set(
            new IndexRepository()
        );
        config.tableRepositoryConfig().set(
            new TableRepository()
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
        config.columnFormatManagerConfig().set(
            new ColumnFormatManager(
                config.getColumnFormatConfig(),
                config.loggerConfig()
            )
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
                config.columnFormatManagerConfig().get()
            )
        );
        config.indexFactoryConfig().set(
            new IndexFactory()
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