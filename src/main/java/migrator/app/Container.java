package migrator.app;

import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.database.format.ColumnFormatManager;
import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.column.service.ColumnService;
import migrator.app.domain.connection.service.ConnectionFactory;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.service.DatabaseFactory;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.index.IndexRepository;
import migrator.app.domain.index.service.IndexActiveState;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.index.service.IndexService;
import migrator.app.domain.project.service.ProjectFactory;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.domain.table.service.TableService;
import migrator.app.gui.GuiContainer;
import migrator.app.domain.table.TableRepository;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;

import java.util.Collection;

import migrator.app.ConfigContainer;
import migrator.app.code.CodeManager;
import migrator.app.migration.Migration;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;
import migrator.app.router.ActiveRoute;
import migrator.app.toast.ToastService;
import migrator.lib.hotkyes.HotkeyFactory;
import migrator.lib.hotkyes.HotkeysService;
import migrator.lib.logger.Logger;
import migrator.lib.repository.UniqueRepository;
import migrator.lib.storage.Storage;

public class Container {
    protected ConfigContainer config;

    public Container(ConfigContainer config) {
        this.config = config;
    }

    public Logger getLogger() {
        return this.config.loggerConfig().get();
    }

    public ActiveRoute getActiveRoute() {
        return this.config.activeRouteConfig().get();
    }

    public Migration getMigration() {
        return this.config.migrationConfig().get();
    }

    public ColumnFormatManager getColumnFormatManager() {
        return this.config.columnFormatManagerConfig().get();
    }

    public DatabaseDriverManager getDatabaseDriverManager() {
        return this.config.databaseDriverManagerConfig().get();
    }

    public CodeManager getCodeManager() {
        return this.config.codeManagerConfig().get();
    }

    public ConnectionFactory getConnectionFactory() {
        return this.config.connectionFactoryConfig().get();
    };

    public DatabaseFactory getdatabaseFactory() {
        return this.config.databaseFactoryConfig().get();
    };

    public ColumnFactory getColumnFactory() {
        return this.config.columnFactoryConfig().get();
    };

    public IndexFactory getIndexFactory() {
        return this.config.indexFactoryConfig().get();
    }

    public TableFactory getTableFactory() {
        return this.config.tableFactoryConfig().get();
    }

    public ProjectFactory getProjectFactory() {
        return this.config.projectFactoryConfig().get();
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

    public ProjectService getProjectService() {
        return this.config.projectServiceConfig().get();
    }

    public ToastService getToastService() {
        return this.config.toastServiceConfig().get();
    }

    public ColumnActiveState getColumnActiveState() {
        return this.config.columnActiveStateConfig().get();
    }

    public IndexActiveState getIndexActiveState() {
        return this.config.indexActiveStateConfig().get();
    }

    public TableActiveState getTableActiveState() {
        return this.config.tableActiveStateConfig().get();
    }

    public TableRepository getTableRepository() {
        return this.config.tableRepositoryConfig().get();
    }

    public ColumnRepository getColumnRepository() {
        return this.config.columnRepositoryConfig().get();
    }

    public IndexRepository getIndexRepository() {
        return this.config.indexRepositoryConfig().get();
    }

    public HotkeyFactory getHotkeyFactory() {
        return this.config.getHoteyFactory().get();
    }

    public HotkeysService getHotkeyService() {
        return this.config.getHoteyService().get();
    }

    public UniqueRepository<IndexProperty> getIndexPropertyRepository() {
        return this.config.indexPropertyRepositoryConfig().get();
    }

    public Storage<Collection<ChangeCommand>> getChangeCommandStorage() {
        return this.config.changeCommandStorageConfig().get();
    }

    public Storage<Collection<ColumnProperty>> getColumnPropertyStorage() {
        return this.config.columnPropertyStorageConfig().get();
    }

    public Storage<Collection<IndexProperty>> getIndexPropertyStorage() {
        return this.config.indexPropertyStorageConfig().get();
    }

    public Storage<Collection<Column>> getColumnStorage() {
        return this.config.columnStorageConfig().get();
    }

    public Storage<Collection<Index>> getIndexStorage() {
        return this.config.indexStorageConfig().get();
    }

    public UniqueRepository<ChangeCommand> getChangeCommandRepository() {
        return this.config.changeCommandRepositoryConfig().get();
    }

    public UniqueRepository<ColumnProperty> getColumnPropertyRepository() {
        return this.config.columnPropertyRepositoryConfig().get();
    }

    public UniqueRepository<TableProperty> getTablePropertyRepository() {
        return this.config.tablePropertyRepositoryConfig().get();
    }

    public Storage<Collection<Table>> getTableStorage() {
        return this.config.tableStorageConfig().get();
    }

    public Storage<Collection<TableProperty>> getTablePropertyStorage() {
        return this.config.tablePropertyStorageConfig().get();
    }

    public GuiContainer getGuiContainer() {
        return config.guiContainerConfig().get();
    }
}