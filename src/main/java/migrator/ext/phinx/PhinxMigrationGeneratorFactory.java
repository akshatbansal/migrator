package migrator.ext.phinx;

import migrator.app.code.CodeManager;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.lib.stringformatter.PascalCaseFormatter;
import migrator.lib.stringformatter.UnderscoreFormatter;;

public class PhinxMigrationGeneratorFactory implements MigrationGeneratorFactory {
    protected CodeManager codeManager;

    public PhinxMigrationGeneratorFactory(CodeManager codeManager) {
        this.codeManager = codeManager;
    }

    @Override
    public MigrationGenerator create() {
        return new PhinxMigrationGenerator(
            new TimestampFileStorageFactory(
                new UnderscoreFormatter()
            ),
            this.codeManager.getCommandFactory("php"),
            new PascalCaseFormatter()
        );
    }
}