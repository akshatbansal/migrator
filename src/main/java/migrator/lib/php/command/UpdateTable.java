package migrator.lib.php.command;

import migrator.migration.ColumnChange;
import migrator.migration.IndexChange;
import migrator.migration.TableChange;
import migrator.lib.php.PhpCommandFactory;

public class UpdateTable implements PhpCommand {
    protected TableChange tableChange;
    protected PhpCommandFactory commandFactory;

    public UpdateTable(TableChange tableChange, PhpCommandFactory commandFactory) {
        this.tableChange = tableChange;
        this.commandFactory = commandFactory;
    }

    public String toPhp() {
        String php = "$this->table('" + this.tableChange.getOriginalName() + "')\n";
        if (this.tableChange.isNameChanged()) {
            php += "\t->renameTable('" + this.tableChange.getName() + "')\n";
        }
        for (ColumnChange columnChange : this.tableChange.getColumnsChanges()) {
            PhpCommand columnPhpCommand = this.commandFactory.column(columnChange);
            php += columnPhpCommand.toPhp();
        }
        for (IndexChange indexChange : this.tableChange.getIndexesChanges()) {
            PhpCommand columnPhpCommand = this.commandFactory.index(indexChange);
            php += columnPhpCommand.toPhp();
        }
        return  php + 
            "\t->update();\n";
    }
}