package migrator.ext.sql.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.lib.stringtemplate.StringTemplate;

public class AddColumnCommand implements CodeCommand {
    protected String tableName;
    protected ColumnChange change;

    public AddColumnCommand(String tableName, ColumnChange change) {
        this.tableName = tableName;
        this.change = change;
    }

    @Override
    public String toCode() {
        StringTemplate template = new ColumnTemplate(this.change);
        return "ALTER TABLE " + this.tableName + " ADD " + template.render() + ";";
    }
}