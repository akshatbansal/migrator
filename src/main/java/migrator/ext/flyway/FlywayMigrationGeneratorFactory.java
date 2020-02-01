package migrator.ext.flyway;

import migrator.app.code.CodeContainer;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.lib.filesystem.Filesystem;
import migrator.lib.stringformatter.UnderscoreFormatter;

public class FlywayMigrationGeneratorFactory implements MigrationGeneratorFactory {
    protected CodeContainer codeContainer;
    private Filesystem filesystem;

    public FlywayMigrationGeneratorFactory(CodeContainer codeContainer, Filesystem filesystem) {
        this.codeContainer = codeContainer;
        this.filesystem = filesystem;
    }

    @Override
    public MigrationGenerator create() {
        return new FlywayMigrationGenerator(
            this.codeContainer.getCommandFactory("sql"),
            new DateVersionFileStorageFactory(
                this.filesystem,
                new UnderscoreFormatter()
            )
        );
    }
}