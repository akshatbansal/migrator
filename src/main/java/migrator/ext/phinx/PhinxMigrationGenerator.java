package migrator.ext.phinx;

import java.util.Arrays;
import java.util.List;

import migrator.lib.storage.Storage;
import migrator.app.code.CodeCommand;
import migrator.app.code.CodeCommandFactory;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.model.TableChange;

public class PhinxMigrationGenerator implements MigrationGenerator {
    protected Storage storage;
    protected CodeCommandFactory commandFactory;

    public PhinxMigrationGenerator(Storage storage, CodeCommandFactory commandFactory) {
        this.storage = storage;
        this.commandFactory = commandFactory;
    }

    public Boolean generateMigration(String name, List<? extends TableChange> changes) {
        String phinxContent = "";
        for (TableChange tableChange : changes) {
            phinxContent += this.toPhinxFormat(tableChange);
        }
        if (phinxContent.isEmpty()) {
            return true;
        }

        this.storage.store(
            this.wrapToPhinxClass(name, phinxContent)
        );

        return true;
    }

    public Boolean generateMigration(String name, TableChange ... changes) {
        return this.generateMigration(name, Arrays.asList(changes));
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