package migrator.ext.phinx;

import java.util.Arrays;
import java.util.List;

import migrator.common.Storage;
import migrator.migration.MigrationGenerator;
import migrator.migration.TableChange;
import migrator.lib.php.PhpCommandFactory;
import migrator.lib.php.command.PhpCommand;

public class PhinxMigrationGenerator implements MigrationGenerator {
    protected Storage storage;
    protected PhpCommandFactory commandFactory;

    public PhinxMigrationGenerator(Storage storage, PhpCommandFactory commandFactory) {
        this.storage = storage;
        this.commandFactory = commandFactory;
    }

    public Boolean generateMigration(List<TableChange> changes) {
        String phinxContent = "";
        for (TableChange tableChange : changes) {
            phinxContent += this.toPhinxFormat(tableChange);
        }
        if (phinxContent.isEmpty()) {
            return true;
        }

        this.storage.store(
            this.wrapToPhinxClass(phinxContent)
        );

        return true;
    }

    public Boolean generateMigration(TableChange ... changes) {
        return this.generateMigration(Arrays.asList(changes));
    }

    private String toPhinxFormat(TableChange tableChange) {
        PhpCommand phpCommand = this.commandFactory.table(tableChange);
        return phpCommand.toPhp();
    }

    private String wrapToPhinxClass(String changeContent) {
        String tabedContent = "";
        for (String line : changeContent.split("\n")) {
            tabedContent += "\t\t" + line + "\n";
        }
        return "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    tabedContent +
                "\t}\n" +
            "}\n";
    }
}