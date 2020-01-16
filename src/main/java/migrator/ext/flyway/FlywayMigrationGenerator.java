package migrator.ext.flyway;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import migrator.app.code.CodeCommand;
import migrator.app.code.CodeCommandFactory;
import migrator.app.migration.FileStorageFactory;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.model.TableChange;
import migrator.lib.result.BooleanResult;
import migrator.lib.storage.Storage;

public class FlywayMigrationGenerator implements MigrationGenerator {
    protected CodeCommandFactory codeCommandFactory;
    protected FileStorageFactory storageFactory;

    public FlywayMigrationGenerator(
        CodeCommandFactory codeCommandFactory,
        FileStorageFactory storageFactory
    ) {
        this.codeCommandFactory = codeCommandFactory;
        this.storageFactory = storageFactory;
    }

    @Override
    public BooleanResult generateMigration(String projectFolder, String name, List<? extends TableChange> changes) {
        String flywayContent = "";
        for (TableChange tableChange : changes) {
            flywayContent += this.toFlywayFormat(tableChange);
        }
        if (flywayContent.isEmpty()) {
            return new BooleanResult();
        }

        Storage<String> storage = this.storageFactory.create(
            new File(projectFolder + System.getProperty("file.separator") + name)
        );
        storage.store(flywayContent);

        return new BooleanResult();
    }

    @Override
    public BooleanResult generateMigration(String projectFolder, String name, TableChange... changes) {
        return this.generateMigration(projectFolder, name, Arrays.asList(changes));
    }

    private String toFlywayFormat(TableChange tableChange) {
        CodeCommand codeCommand = this.codeCommandFactory.table(tableChange);
        return codeCommand.toCode();
    }
}