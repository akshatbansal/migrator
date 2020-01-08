package migrator.ext.sql;

import java.util.ArrayList;
import java.util.List;

import migrator.app.code.CodeCommand;
import migrator.app.code.CodeCommandFactory;
import migrator.app.code.CommandSequence;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.ext.sql.command.AddColumnCommand;
import migrator.ext.sql.command.ChangeColumnCommand;
import migrator.ext.sql.command.CreateIndexCommand;
import migrator.ext.sql.command.CreateTableCommand;
import migrator.ext.sql.command.DropIndexCommand;
import migrator.ext.sql.command.DropTableCommand;
import migrator.ext.sql.command.RemoveColumnCommand;
import migrator.ext.sql.command.RenameTableCommand;
import migrator.ext.sql.command.VoidCommand;

public class SqlCommandFactory implements CodeCommandFactory {
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;

    public SqlCommandFactory(ApplicationColumnFormatCollection applicationColumnFormatCollection) {
        this.applicationColumnFormatCollection = applicationColumnFormatCollection;
    }

    @Override
    public CodeCommand column(ColumnChange columnChange, TableChange tableChange) {
        if (columnChange.getCommand().isType(ChangeCommand.CREATE)) {
            return new AddColumnCommand(tableChange.getName(), columnChange, this.applicationColumnFormatCollection);
        } else if (columnChange.getCommand().isType(ChangeCommand.DELETE)) {
            return new RemoveColumnCommand(tableChange.getName(), columnChange.getName());
        } else if (columnChange.getCommand().isType(ChangeCommand.UPDATE)) {
            return new ChangeColumnCommand(tableChange.getName(), columnChange, this.applicationColumnFormatCollection);
        }
        return new VoidCommand();
    }

    @Override
    public CodeCommand index(IndexChange indexChange, TableChange tableChange) {
        if (indexChange.getCommand().isType(ChangeCommand.DELETE)) {
            return new DropIndexCommand(indexChange, tableChange.getName());
        } else if (indexChange.getCommand().isType(ChangeCommand.CREATE)) {
            return new CreateIndexCommand(tableChange.getName(), indexChange);
        }
        return new VoidCommand();
    }

    @Override
    public CodeCommand table(TableChange tableChange) {
        if (tableChange.getCommand().isType(ChangeCommand.UPDATE)) {
            return new RenameTableCommand(tableChange);
        } else if (tableChange.getCommand().isType(ChangeCommand.CREATE)) {
            return new CreateTableCommand(tableChange, this.applicationColumnFormatCollection);
        } else if (tableChange.getCommand().isType(ChangeCommand.DELETE)) {
            return new DropTableCommand(tableChange);
        }
        List<CodeCommand> commands = new ArrayList<>();
        for (ColumnChange columnChange : tableChange.getColumnsChanges()) {
            commands.add(this.column(columnChange, tableChange));
        }
        for (IndexChange indexChange : tableChange.getIndexesChanges()) {
            commands.add(this.index(indexChange, tableChange));
        }
        return new CommandSequence(commands);
    }
}