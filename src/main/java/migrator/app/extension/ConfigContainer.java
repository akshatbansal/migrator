package migrator.app.extension;

import migrator.app.code.CodeConfig;
import migrator.app.database.driver.DatabaseDriverConfig;
import migrator.app.migration.MigrationConfig;

public class ConfigContainer {
    protected MigrationConfig migrationConfig;
    protected DatabaseDriverConfig databaseDriverConfig;
    protected CodeConfig codeConfig;

    public ConfigContainer() {
        this.migrationConfig = new MigrationConfig();
        this.databaseDriverConfig = new DatabaseDriverConfig();
        this.codeConfig = new CodeConfig();
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
}