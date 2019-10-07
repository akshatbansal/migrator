package migrator.app;

import migrator.app.code.CodeConfig;
import migrator.app.code.CodeManager;
import migrator.app.database.driver.DatabaseDriverConfig;
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

    protected ValueConfig<ColumnFactory> columnFactory;
    protected ValueConfig<IndexFactory> indexFactory;
    protected ValueConfig<TableFactory> tableFactory;
    protected ValueConfig<TableChangeFactory> tableChangeFactory;

    protected ValueConfig<ConnectionService> connectionService;
    protected ValueConfig<DatabaseService> databaseService;
    protected ValueConfig<TableService> tableService;
    protected ValueConfig<ColumnService> columnService;
    protected ValueConfig<IndexService> indexService;
    protected ValueConfig<ChangeService> changeService;

    public ConfigContainer() {
        this.migrationConfig = new MigrationConfig();
        this.databaseDriverConfig = new DatabaseDriverConfig();
        this.codeConfig = new CodeConfig();

        this.activeRoute = new ValueConfig<>();
        this.migration = new ValueConfig<>();
        this.databaseDriverManager = new ValueConfig<>();
        this.codeManager = new ValueConfig<>();

        this.tableChangeFactory = new ValueConfig<>();
        this.tableFactory = new ValueConfig<>();
        this.columnFactory = new ValueConfig<>();
        this.indexFactory = new ValueConfig<>();

        this.connectionService = new ValueConfig<>();
        this.databaseService = new ValueConfig<>();
        this.tableService = new ValueConfig<>();
        this.columnService = new ValueConfig<>();
        this.indexService = new ValueConfig<>();
        this.changeService = new ValueConfig<>();
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

    public ValueConfig<TableChangeFactory> tableChangeFactoryConfig() {
        return this.tableChangeFactory;
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

    public ValueConfig<ChangeService> changeServiceConfig() {
        return this.changeService;
    }
}