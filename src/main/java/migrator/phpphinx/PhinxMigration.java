package migrator.phpphinx;

import java.util.Arrays;
import java.util.List;

import migrator.common.Storage;
import migrator.migration.Migration;
import migrator.migration.TableChange;
import migrator.phpphinx.command.PhpCommand;

public class PhinxMigration implements Migration {
    protected Storage storage;
    protected PhpCommandFactory commandFactory;

    public PhinxMigration(Storage storage, PhpCommandFactory commandFactory) {
        this.storage = storage;
        this.commandFactory = commandFactory;
    }

    public Boolean create(List<TableChange> changes) {
        String phinxContent = "";
        for (TableChange tableChange : changes) {
            phinxContent += this.toPhinxFormat(tableChange);
        }
        this.storage.store(phinxContent);

        return true;
    }

    public Boolean create(TableChange ... changes) {
        return this.create(Arrays.asList(changes));
    }

    private String toPhinxFormat(TableChange tableChange) {
        PhpCommand phpCommand = this.commandFactory.table(tableChange);
        return phpCommand.toPhp();
    }
}