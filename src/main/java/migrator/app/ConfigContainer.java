package migrator.app;

import migrator.app.code.CodeConfig;
import migrator.app.code.CodeManager;
import migrator.app.database.driver.DatabaseDriverConfig;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.database.format.ColumnFormat;
import migrator.app.database.format.ColumnFormatManager;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.column.service.ColumnRepository;
import migrator.app.domain.column.service.ColumnService;
import migrator.app.domain.connection.service.ConnectionFactory;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.service.DatabaseFactory;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.index.service.IndexActiveState;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.index.service.IndexRepository;
import migrator.app.domain.index.service.IndexService;
import migrator.app.domain.project.service.ProjectFactory;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.domain.table.service.TableRepository;
import migrator.app.domain.table.service.TableService;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.Migration;
import migrator.app.migration.MigrationConfig;
import migrator.app.router.ActiveRoute;
import migrator.app.toast.ToastService;
import migrator.lib.config.MapConfig;
import migrator.lib.config.ValueConfig;
import migrator.lib.logger.Logger;

public class ConfigContainer {
    private MigrationConfig migrationConfig;
    private DatabaseDriverConfig databaseDriverConfig;
    private CodeConfig codeConfig;
    private MapConfig<ColumnFormat> columnFormatConfig;

    private ValueConfig<ActiveRoute> activeRoute;
    private ValueConfig<Migration> migration;
    private ValueConfig<DatabaseDriverManager> databaseDriverManager;
    private ValueConfig<CodeManager> codeManager;
    private ValueConfig<ColumnFormatManager> columnFormatManager;
    private ValueConfig<Logger> logger;

    private ValueConfig<ConnectionFactory> connectionFactory;
    private ValueConfig<DatabaseFactory> databaseFactory;
    private ValueConfig<ColumnFactory> columnFactory;
    private ValueConfig<IndexFactory> indexFactory;
    private ValueConfig<TableFactory> tableFactory;
    private ValueConfig<ProjectFactory> projectFactory;

    private ValueConfig<ConnectionService> connectionService;
    private ValueConfig<DatabaseService> databaseService;
    private ValueConfig<TableService> tableService;
    private ValueConfig<ColumnService> columnService;
    private ValueConfig<IndexService> indexService;
    private ValueConfig<ProjectService> projectService;
    private ValueConfig<ToastService> toastService;

    private ValueConfig<TableActiveState> tableActiveState;
    private ValueConfig<ColumnActiveState> columnActiveState;
    private ValueConfig<IndexActiveState> indexActiveState;

    private ValueConfig<TableRepository> tableRepository;
    private ValueConfig<ColumnRepository> columnRepository;
    private ValueConfig<IndexRepository> indexRepository;

    public ConfigContainer() {
        this.migrationConfig = new MigrationConfig();
        this.databaseDriverConfig = new DatabaseDriverConfig();
        this.codeConfig = new CodeConfig();
        this.columnFormatConfig = new MapConfig<>();
        this.logger = new ValueConfig<>();

        this.activeRoute = new ValueConfig<>();
        this.migration = new ValueConfig<>();
        this.databaseDriverManager = new ValueConfig<>();
        this.codeManager = new ValueConfig<>();
        this.columnFormatManager = new ValueConfig<>();

        this.connectionFactory = new ValueConfig<>();
        this.databaseFactory = new ValueConfig<>();
        this.tableFactory = new ValueConfig<>();
        this.columnFactory = new ValueConfig<>();
        this.indexFactory = new ValueConfig<>();
        this.projectFactory = new ValueConfig<>();

        this.connectionService = new ValueConfig<>();
        this.databaseService = new ValueConfig<>();
        this.tableService = new ValueConfig<>();
        this.columnService = new ValueConfig<>();
        this.indexService = new ValueConfig<>();
        this.projectService = new ValueConfig<>();
        this.toastService = new ValueConfig<>();

        this.columnActiveState = new ValueConfig<>();
        this.indexActiveState = new ValueConfig<>();
        this.tableActiveState = new ValueConfig<>();

        this.tableRepository = new ValueConfig<>();
        this.columnRepository = new ValueConfig<>();
        this.indexRepository = new ValueConfig<>();
    }

    public MapConfig<ColumnFormat> getColumnFormatConfig() {
        return this.columnFormatConfig;
    }

    public MigrationConfig getMigrationConfig() {
        return this.migrationConfig;
    }

    public DatabaseDriverConfig getDatabaseDriverConfig() {
        return this.databaseDriverConfig;
    }

    public CodeConfig getCodeConfig() {
        return this.codeConfig;
    }

    public ValueConfig<ActiveRoute> activeRouteConfig() {
        return this.activeRoute;
    }

    public ValueConfig<Migration> migrationConfig() {
        return this.migration;
    }

    public ValueConfig<Logger> loggerConfig() {
        return this.logger;
    }

    public ValueConfig<ColumnFormatManager> columnFormatManagerConfig() {
        return this.columnFormatManager;
    }

    public ValueConfig<DatabaseDriverManager> databaseDriverManagerConfig() {
        return this.databaseDriverManager;
    }

    public ValueConfig<CodeManager> codeManagerConfig() {
        return this.codeManager;
    }

    public ValueConfig<ConnectionFactory> connectionFactoryConfig() {
        return this.connectionFactory;
    }

    public ValueConfig<DatabaseFactory> databaseFactoryConfig() {
        return this.databaseFactory;
    }

    public ValueConfig<TableFactory> tableFactoryConfig() {
        return this.tableFactory;
    }

    public ValueConfig<ColumnFactory> columnFactoryConfig() {
        return this.columnFactory;
    }

    public ValueConfig<IndexFactory> indexFactoryConfig() {
        return this.indexFactory;
    }

    public ValueConfig<ProjectFactory> projectFactoryConfig() {
        return this.projectFactory;
    }

    public ValueConfig<ConnectionService> connectionServiceConfig() {
        return this.connectionService;
    }

    public ValueConfig<DatabaseService> databaseServiceConfig() {
        return this.databaseService;
    }

    public ValueConfig<TableService> tableServiceConfig() {
        return this.tableService;
    }

    public ValueConfig<ColumnService> columnServiceConfig() {
        return this.columnService;
    }

    public ValueConfig<IndexService> indexServiceConfig() {
        return this.indexService;
    }

    public ValueConfig<ProjectService> projectServiceConfig() {
        return this.projectService;
    }

    public ValueConfig<ToastService> toastServiceConfig() {
        return this.toastService;
    }

    public ValueConfig<ColumnActiveState> columnActiveStateConfig() {
        return this.columnActiveState;
    }

    public ValueConfig<IndexActiveState> indexActiveStateConfig() {
        return this.indexActiveState;
    }

    public ValueConfig<TableActiveState> tableActiveStateConfig() {
        return this.tableActiveState;
    }

    public ValueConfig<TableRepository> tableRepositoryConfig() {
        return this.tableRepository;
    }

    public ValueConfig<ColumnRepository> columnRepositoryConfig() {
        return this.columnRepository;
    }

    public ValueConfig<IndexRepository> indexRepositoryConfig() {
        return this.indexRepository;
    }
}