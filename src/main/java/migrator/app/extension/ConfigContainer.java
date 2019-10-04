package migrator.app.extension;

import migrator.app.database.driver.DatabaseDriverConfig;
import migrator.app.migration.MigrationConfig;

public class ConfigContainer {
    protected MigrationConfig migrationConfig;
    protected DatabaseDriverConfig databaseDriverConfig;

    public ConfigContainer() {
        this.migrationConfig = new MigrationConfig();
        this.databaseDriverConfig = new DatabaseDriverConfig();
    }

    public MigrationConfig getMigrationConfig() {
        return this.migrationConfig;
    }

    public DatabaseDriverConfig getDatabaseDriverConfig() {
        return this.databaseDriverConfig;
    }
}