package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.ext.php.PhpCommandFactory;

public class UpdateTable implements CodeCommand {
    protected TableChange tableChange;
    protected PhpCommandFactory commandFactory;

    public UpdateTable(TableChange tableChange, PhpCommandFactory commandFactory) {
        this.tableChange = tableChange;
        this.commandFactory = commandFactory;
    }

    @Override
    public String toCode() {
        String php = "$this->table('" + this.tableChange.getOriginalName() + "')\n";
        if (this.tableChange.hasNameChanged()) {
            php += "\t->rename('" + this.tableChange.getName() + "')\n";
        }
        for (ColumnChange columnChange : this.tableChange.getColumnsChanges()) {
            CodeCommand columnPhpCommand = this.commandFactory.column(columnChange, this.tableChange);
            php += columnPhpCommand.toCode();
        }
        for (IndexChange indexChange : this.tableChange.getIndexesChanges()) {
            CodeCommand columnPhpCommand = this.commandFactory.index(indexChange, this.tableChange);
            php += columnPhpCommand.toCode();
        }
        return  php + 
            "\t->update();\n";
    }
}