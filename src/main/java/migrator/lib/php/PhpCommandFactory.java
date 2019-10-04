package migrator.lib.php;

import migrator.migration.ChangeCommand;
import migrator.migration.ColumnChange;
import migrator.migration.IndexChange;
import migrator.migration.TableChange;
import migrator.lib.php.command.AddColumn;
import migrator.lib.php.command.AddIndex;
import migrator.lib.php.command.ChangeColumn;
import migrator.lib.php.command.DropTable;
import migrator.lib.php.command.PhpCommand;
import migrator.lib.php.command.RemoveColumn;
import migrator.lib.php.command.RemoveIndexByName;
import migrator.lib.php.command.SaveTable;
import migrator.lib.php.command.UpdateTable;

public class PhpCommandFactory {
    public DropTable dropTable(TableChange tableChange) {
        return new DropTable(tableChange);
    }

    public PhpCommand column(ColumnChange columnChange) {
        if (columnChange.getCommand().isType(ChangeCommand.DELETE)) {
            return this.removeColumn(columnChange);
        } else if (columnChange.getCommand().isType(ChangeCommand.UPDATE)) {
            return this.changeColumn(columnChange);
        }
        return this.addColumn(columnChange);
    }

    public PhpCommand table(TableChange tableChange) {
        if (tableChange.getCommand().isType(ChangeCommand.DELETE)) {
            return this.dropTable(tableChange);
        } else if (tableChange.getCommand().isType(ChangeCommand.UPDATE)) {
            return this.updateTable(tableChange);
        }
        return this.saveTable(tableChange);
    }

    public PhpCommand index(IndexChange indexChange) {
        if (indexChange.getCommand().isType(ChangeCommand.DELETE)) {
            return this.removeIndexByname(indexChange);
        }
        return this.addIndex(indexChange);
    }

    public SaveTable saveTable(TableChange tableChange) {
        return new SaveTable(tableChange, this);
    }

    public UpdateTable updateTable(TableChange tableChange) {
        return new UpdateTable(tableChange, this);
    }

    public AddColumn addColumn(ColumnChange columnChange) {
        return new AddColumn(columnChange);
    }

    public RemoveColumn removeColumn(ColumnChange columnChange) {
        return new RemoveColumn(columnChange);
    }

    public ChangeColumn changeColumn(ColumnChange columnChange) {
        return new ChangeColumn(columnChange);
    }

    public AddIndex addIndex(IndexChange indexChange) {
        return new AddIndex(indexChange);
    }

    public RemoveIndexByName removeIndexByname(IndexChange indexChange) {
        return new RemoveIndexByName(indexChange);
    }
}