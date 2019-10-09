package migrator.app.migration;

import java.util.List;

import migrator.app.migration.model.TableChange;

public interface MigrationGenerator {
    public Boolean generateMigration(String name, List<? extends TableChange> changes);
    public Boolean generateMigration(String name, TableChange ... changes);
}