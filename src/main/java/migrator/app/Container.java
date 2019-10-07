package migrator.app;

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
import migrator.app.code.CodeManager;
import migrator.app.migration.Migration;
import migrator.app.router.ActiveRoute;

public class Container {
    protected ConfigContainer config;

    public Container(ConfigContainer config) {
        this.config = config;
    }

    public ActiveRoute getActiveRoute() {
        return this.config.activeRouteConfig().get();
    }

    public Migration getMigration() {
        return this.config.migrationConfig().get();
    }

    public DatabaseDriverManager getDatabaseDriverManager() {
        return this.config.databaseDriverManagerConfig().get();
    }

    public CodeManager getCodeManager() {
        return this.config.codeManagerConfig().get();
    }

    public ColumnFactory getColumnFactory() {
        return this.config.columnFactoryConfig().get();
    };

    public IndexFactory getIndexFactory() {
        return this.config.indexFactoryConfig().get();
    }

    public TableFactory getTableFactory() {
        return this.config.tableFactoryConfig().get();
    }

    public TableChangeFactory getTableChangeFactory() {
        return this.config.tableChangeFactoryConfig().get();
    }

    public ConnectionService getConnectionService() {
        return this.config.connectionServiceConfig().get();
    }

    public DatabaseService getDatabaseService() {
        return this.config.databaseServiceConfig().get();
    }

    public TableService getTableService() {
        return this.config.tableServiceConfig().get();
    }

    public ColumnService getColumnService() {
        return this.config.columnServiceConfig().get();
    }

    public IndexService getIndexService() {
        return this.config.indexServiceConfig().get();
    }

    public ChangeService getChangeService() {
        return this.config.changeServiceConfig().get();
    }
}