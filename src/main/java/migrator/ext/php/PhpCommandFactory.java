package migrator.ext.php;

import migrator.app.code.CodeCommand;
import migrator.app.code.CodeCommandFactory;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.ext.php.command.AddColumn;
import migrator.ext.php.command.AddIndex;
import migrator.ext.php.command.ChangeColumn;
import migrator.ext.php.command.CreateTable;
import migrator.ext.php.command.DropTable;
import migrator.ext.php.command.RemoveColumn;
import migrator.ext.php.command.RemoveIndexByName;
import migrator.ext.php.command.SaveTable;
import migrator.ext.php.command.UpdateTable;
import migrator.ext.php.command.VoidCommand;

public class PhpCommandFactory implements CodeCommandFactory {
    public DropTable dropTable(TableChange tableChange) {
        return new DropTable(tableChange);
    }

    public CodeCommand column(ColumnChange columnChange) {
        if (columnChange.getCommand().isType(ChangeCommand.DELETE)) {
            return this.removeColumn(columnChange);
        } else if (columnChange.getCommand().isType(ChangeCommand.UPDATE)) {
            return this.changeColumn(columnChange);
        } else if (columnChange.getCommand().isType(ChangeCommand.CREATE)) {
            return this.addColumn(columnChange);
        }
        return new VoidCommand();
    }

    public CodeCommand table(TableChange tableChange) {
        if (tableChange.getCommand().isType(ChangeCommand.DELETE)) {
            return this.dropTable(tableChange);
        } else if (tableChange.getCommand().isType(ChangeCommand.UPDATE)) {
            return this.updateTable(tableChange);
        } else if (tableChange.getCommand().isType(ChangeCommand.CREATE)) {
            return this.createTable(tableChange);
        }
        return this.saveTable(tableChange);
    }

    public CodeCommand index(IndexChange indexChange) {
        if (indexChange.getCommand().isType(ChangeCommand.NONE)) {
            return new VoidCommand();
        }
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

    public CreateTable createTable(TableChange tableChange) {
        return new CreateTable(tableChange, this);
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