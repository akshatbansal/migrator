package migrator.ext.sql.command;

import migrator.app.code.CodeCommand;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.migration.model.ColumnChange;
import migrator.lib.stringtemplate.StringTemplate;

public class AddColumnCommand implements CodeCommand {
    protected String tableName;
    protected ColumnChange change;
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;

    public AddColumnCommand(String tableName, ColumnChange change, ApplicationColumnFormatCollection applicationColumnFormatCollection) {
        this.tableName = tableName;
        this.change = change;
        this.applicationColumnFormatCollection = applicationColumnFormatCollection;
    }

    @Override
    public String toCode() {
        StringTemplate template = new ColumnTemplate(this.change, this.applicationColumnFormatCollection.getFormatByName(this.change.getFormat()));
        return "ALTER TABLE `" + this.tableName + "` ADD " + template.render() + ";";
    }
}