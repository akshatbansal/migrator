package migrator.ext.phinx;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import migrator.lib.storage.Storage;
import migrator.lib.stringformatter.StringFormatter;
import migrator.app.code.CodeCommand;
import migrator.app.code.CodeCommandFactory;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.model.TableChange;

public class PhinxMigrationGenerator implements MigrationGenerator {
    protected TimestampFileStorageFactory timestampFileStorageFactory;
    protected CodeCommandFactory commandFactory;
    protected StringFormatter classNameFormatter;

    public PhinxMigrationGenerator(
        TimestampFileStorageFactory timestampFileStorageFactory,
        CodeCommandFactory commandFactory,
        StringFormatter classNameFormatter
    ) {
        this.timestampFileStorageFactory = timestampFileStorageFactory;
        this.commandFactory = commandFactory;
        this.classNameFormatter = classNameFormatter;
    }

    public Boolean generateMigration(String projectFolder, String name, List<? extends TableChange> changes) {
        String phinxContent = "";
        for (TableChange tableChange : changes) {
            phinxContent += this.toPhinxFormat(tableChange);
        }
        if (phinxContent.isEmpty()) {
            return true;
        }

        Storage storage = this.timestampFileStorageFactory.create(
            new File(projectFolder + System.getProperty("file.separator") + name)
        );
        String className = this.classNameFormatter.format(name);
        storage.store(
            this.wrapToPhinxClass(className, phinxContent)
        );

        return true;
    }

    public Boolean generateMigration(String projectFolder, String name, TableChange ... changes) {
        return this.generateMigration(projectFolder, name, Arrays.asList(changes));
    }

    private String toPhinxFormat(TableChange tableChange) {
        CodeCommand codeCommand = this.commandFactory.table(tableChange);
        return codeCommand.toCode();
    }

    private String wrapToPhinxClass(String className, String changeContent) {
        String tabedContent = "";
        for (String line : changeContent.split("\n")) {
            tabedContent += "\t\t" + line + "\n";
        }
        return "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class " + className + " extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    tabedContent +
                "\t}\n" +
            "}\n";
    }
}