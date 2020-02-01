package migrator.ext.phinx;

import migrator.app.code.CodeContainer;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.app.migration.SimpleFileStorageFactory;
import migrator.lib.filesystem.Filesystem;

public class PhinxMigrationGeneratorFactory implements MigrationGeneratorFactory {
    protected CodeContainer codeContainer;
    private Filesystem filesystem;

    public PhinxMigrationGeneratorFactory(CodeContainer codeContainer, Filesystem filesystem) {
        this.codeContainer = codeContainer;
        this.filesystem = filesystem;
    }

    @Override
    public MigrationGenerator create() {
        return new PhinxMigrationGenerator(
            new SimpleFileStorageFactory(this.filesystem),
            this.codeContainer.getCommandFactory("php")
        );
    }
}