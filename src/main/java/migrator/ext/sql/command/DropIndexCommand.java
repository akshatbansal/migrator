package migrator.ext.sql.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.IndexChange;

public class DropIndexCommand implements CodeCommand {
    protected String tableName;
    protected IndexChange change;

    public DropIndexCommand(IndexChange change, String tableName) {
        this.change = change;
        this.tableName = tableName;
    }

    @Override
    public String toCode() {
        return "ALTER TABLE " + this.tableName + " DROP INDEX " + this.change.getName() + ";";
    }
}