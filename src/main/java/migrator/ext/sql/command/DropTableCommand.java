package migrator.ext.sql.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.TableChange;

public class DropTableCommand implements CodeCommand {
    protected TableChange change;

    public DropTableCommand(TableChange change) {
        this.change = change;
    }

    @Override
    public String toCode() {
        return "DROP TABLE `" + this.change.getOriginalName() + "`;";
    }
}