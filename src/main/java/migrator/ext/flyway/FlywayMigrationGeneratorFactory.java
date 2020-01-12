package migrator.ext.flyway;

import migrator.app.code.CodeContainer;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.lib.stringformatter.UnderscoreFormatter;

public class FlywayMigrationGeneratorFactory implements MigrationGeneratorFactory {
    protected CodeContainer codeContainer;

    public FlywayMigrationGeneratorFactory(CodeContainer codeContainer) {
        this.codeContainer = codeContainer;
    }

    @Override
    public MigrationGenerator create() {
        return new FlywayMigrationGenerator(
            this.codeContainer.getCommandFactory("sql"),
            new DateVersionFileStorageFactory(
                new UnderscoreFormatter()
            )
        );
    }
}