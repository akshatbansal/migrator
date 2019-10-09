package migrator.app;

import migrator.app.code.CodeConfig;
import migrator.app.code.CodeManager;
import migrator.app.database.driver.DatabaseDriverConfig;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.column.service.ColumnService;
import migrator.app.domain.connection.service.ConnectionFactory;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.service.DatabaseFactory;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.index.service.IndexService;
import migrator.app.domain.project.service.ProjectFactory;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.domain.table.service.TableService;
import migrator.app.migration.Migration;
import migrator.app.migration.MigrationConfig;
import migrator.app.router.ActiveRoute;
import migrator.lib.config.ValueConfig;

public class ConfigContainer {
    protected MigrationConfig migrationConfig;
    protected DatabaseDriverConfig databaseDriverConfig;
    protected CodeConfig codeConfig;

    protected ValueConfig<ActiveRoute> activeRoute;
    protected ValueConfig<Migration> migration;
    protected ValueConfig<DatabaseDriverManager> databaseDriverManager;
    protected ValueConfig<CodeManager> codeManager;

    protected ValueConfig<ConnectionFactory> connectionFactory;
    protected ValueConfig<DatabaseFactory> databaseFactory;
    protected ValueConfig<ColumnFactory> columnFactory;
    protected ValueConfig<IndexFactory> indexFactory;
    protected ValueConfig<TableFactory> tableFactory;
    protected ValueConfig<ProjectFactory> projectFactory;

    protected ValueConfig<ConnectionService> connectionService;
    protected ValueConfig<DatabaseService> databaseService;
    protected ValueConfig<TableService> tableService;
    protected ValueConfig<ColumnService> columnService;
    protected ValueConfig<IndexService> indexService;
    protected ValueConfig<ProjectService> projectService;

    public ConfigContainer() {
        this.migrationConfig = new MigrationConfig();
        this.databaseDriverConfig = new DatabaseDriverConfig();
        this.codeConfig = new CodeConfig();

        this.activeRoute = new ValueConfig<>();
        this.migration = new ValueConfig<>();
        this.databaseDriverManager = new ValueConfig<>();
        this.codeManager = new ValueConfig<>();

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
}