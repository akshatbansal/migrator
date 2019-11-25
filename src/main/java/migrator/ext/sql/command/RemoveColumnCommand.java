package migrator.ext.sql.command;

import migrator.app.code.CodeCommand;

public class RemoveColumnCommand implements CodeCommand {
    protected String tableName;
    protected String columnName;

    public RemoveColumnCommand(String tableName, String columnName) {
        this.columnName = columnName;
        this.tableName = tableName;
    }

    @Override
    public String toCode() {
        return "ALTER TABLE `" + this.tableName + "` DROP COLUMN `" + this.columnName + "`;";
    }
}