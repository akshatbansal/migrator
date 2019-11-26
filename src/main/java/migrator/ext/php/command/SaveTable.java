package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.ext.php.PhpCommandFactory;

public class SaveTable implements CodeCommand {
    protected TableChange tableChange;
    protected PhpCommandFactory commandFactory;

    public SaveTable(TableChange tableChange, PhpCommandFactory commandFactory) {
        this.tableChange = tableChange;
        this.commandFactory = commandFactory;
    }

    @Override
    public String toCode() {
        String tablePhp = "$this->table('" + this.tableChange.getOriginalName() + "')\n";
        String columnPhp = "";
        String indexPhp = "";
        for (ColumnChange columnChange : this.tableChange.getColumnsChanges()) {
            CodeCommand columnPhpCommand = this.commandFactory.column(columnChange, this.tableChange);
            columnPhp += columnPhpCommand.toCode();
        }
        for (IndexChange indexChange : this.tableChange.getIndexesChanges()) {
            CodeCommand columnPhpCommand = this.commandFactory.index(indexChange, this.tableChange);
            indexPhp += columnPhpCommand.toCode();
        }

        if (indexPhp.isEmpty() && columnPhp.isEmpty()) {
            return "";
        }

        return  tablePhp + columnPhp + indexPhp +
            "\t->save();\n";
    }
}