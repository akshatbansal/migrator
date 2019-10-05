package migrator.ext.phinx;

import migrator.app.code.CodeManager;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.lib.storage.ConsoleStorage;;

public class PhinxMigrationGeneratorFactory implements MigrationGeneratorFactory {
    protected CodeManager codeManager;

    public PhinxMigrationGeneratorFactory(CodeManager codeManager) {
        this.codeManager = codeManager;
    }

    @Override
    public MigrationGenerator create() {
        return new PhinxMigrationGenerator(
            new ConsoleStorage(),
            this.codeManager.getCommandFactory("php")
        );
    }
}