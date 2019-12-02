package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.ext.php.PhpCommandFactory;

public class CreateTable implements CodeCommand {
    protected TableChange tableChange;
    protected PhpCommandFactory commandFactory;

    public CreateTable(TableChange tableChange, PhpCommandFactory commandFactory) {
        this.tableChange = tableChange;
        this.commandFactory = commandFactory;
    }

    @Override
    public String toCode() {
        if (this.tableChange.getCommand().isType(ChangeCommand.NONE) && this.tableChange.getColumnsChanges().size() == 0 && this.tableChange.getIndexesChanges().size() == 0) {
            return "";
        }
        String php = "$this->table('" + this.tableChange.getOriginalName() + "', ['id' => false])\n";
        for (ColumnChange columnChange : this.tableChange.getColumnsChanges()) {
            CodeCommand columnPhpCommand = this.commandFactory.column(columnChange, this.tableChange);
            php += columnPhpCommand.toCode();
        }
        for (IndexChange indexChange : this.tableChange.getIndexesChanges()) {
            CodeCommand columnPhpCommand = this.commandFactory.index(indexChange, this.tableChange);
            php += columnPhpCommand.toCode();
        }
        return  php + 
            "\t->create();\n";
    }
}