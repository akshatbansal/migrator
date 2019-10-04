package migrator.app.extension;

import migrator.app.migration.MigrationConfig;

public class ConfigContainer {
    protected MigrationConfig migrationConfig;

    public ConfigContainer() {
        this.migrationConfig = new MigrationConfig();
    }

    public MigrationConfig getMigrationConfig() {
        return this.migrationConfig;
    }
}