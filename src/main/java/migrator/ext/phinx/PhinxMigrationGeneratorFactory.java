package migrator.ext.phinx;

import migrator.app.migration.MigrationGeneratorFactory;
import migrator.migration.MigrationGenerator;
import migrator.lib.php.PhpCommandFactory;
import migrator.lib.storage.ConsoleStorage;;

public class PhinxMigrationGeneratorFactory implements MigrationGeneratorFactory {
    @Override
    public MigrationGenerator create() {
        return new PhinxMigrationGenerator(
            new ConsoleStorage(),
            new PhpCommandFactory()
        );
    }
}