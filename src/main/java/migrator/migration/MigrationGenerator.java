package migrator.migration;

import java.util.List;
import migrator.migration.TableChange;

public interface MigrationGenerator {
    public Boolean generateMigration(List<TableChange> changes);
    public Boolean generateMigration(TableChange ... changes);
}