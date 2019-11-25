package migrator.ext.sql.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.TableChange;

public class RenameTableCommand implements CodeCommand {
    protected TableChange change;

    public RenameTableCommand(TableChange change) {
        this.change = change;
    }

    @Override
    public String toCode() {
        return "ALTER TABLE " + this.change.getOriginalName() + " RENAME TO " + this.change.getName() + ";";
    }
}