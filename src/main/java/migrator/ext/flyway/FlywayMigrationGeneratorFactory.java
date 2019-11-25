package migrator.ext.flyway;

import migrator.app.code.CodeManager;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.lib.stringformatter.UnderscoreFormatter;

public class FlywayMigrationGeneratorFactory implements MigrationGeneratorFactory {
    protected CodeManager codeManager;

    public FlywayMigrationGeneratorFactory(CodeManager codeManager) {
        this.codeManager = codeManager;
    }

    @Override
    public MigrationGenerator create() {
        return new FlywayMigrationGenerator(
            this.codeManager.getCommandFactory("sql"),
            new DateVersionFileStorageFactory(
                new UnderscoreFormatter()
            )
        );
    }
}