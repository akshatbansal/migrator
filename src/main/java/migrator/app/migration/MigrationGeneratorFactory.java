package migrator.app.migration;

import migrator.migration.MigrationGenerator;

public interface MigrationGeneratorFactory {
    public MigrationGenerator create();
}