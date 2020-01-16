package migrator.app.migration;

import java.util.List;

import migrator.app.migration.model.TableChange;
import migrator.lib.result.BooleanResult;

public interface MigrationGenerator {
    public BooleanResult generateMigration(String projectFolder, String name, List<? extends TableChange> changes);
    public BooleanResult generateMigration(String projectFolder, String name, TableChange ... changes);
}