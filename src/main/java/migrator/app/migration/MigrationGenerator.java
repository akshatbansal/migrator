package migrator.app.migration;

import java.util.List;

import migrator.app.migration.model.TableChange;

public interface MigrationGenerator {
    public Boolean generateMigration(List<TableChange> changes);
    public Boolean generateMigration(TableChange ... changes);
}