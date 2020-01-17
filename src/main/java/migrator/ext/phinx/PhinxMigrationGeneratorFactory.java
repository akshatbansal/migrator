package migrator.ext.phinx;

import migrator.app.code.CodeContainer;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.app.migration.SimpleFileStorageFactory;

public class PhinxMigrationGeneratorFactory implements MigrationGeneratorFactory {
    protected CodeContainer codeContainer;

    public PhinxMigrationGeneratorFactory(CodeContainer codeContainer) {
        this.codeContainer = codeContainer;
    }

    @Override
    public MigrationGenerator create() {
        return new PhinxMigrationGenerator(
            new SimpleFileStorageFactory(),
            this.codeContainer.getCommandFactory("php")
        );
    }
}