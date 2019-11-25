package migrator.ext.sql.command;

import java.util.Hashtable;
import java.util.Map;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.lib.stringtemplate.MapStringTemplate;
import migrator.lib.stringtemplate.StringTemplate;

public class SetDefaultCommand implements CodeCommand {
    protected String tableName;
    protected ColumnChange change;

    public SetDefaultCommand(ColumnChange change, String tableName) {
        this.tableName = tableName;
        this.change = change;
    }

    @Override
    public String toCode() {
        Map<String, String> templateReplace = new Hashtable<>();
        templateReplace.put("TABLE", this.tableName);
        templateReplace.put("COLUMN", this.change.getName());
        templateReplace.put("DEFAULT_VALUE", this.change.getDefaultValue());
        StringTemplate template = new MapStringTemplate(
            "ALTER TABLE `{{TABLE}}` ALTER `{{COLUMN}}` SET DEFAULT '{{DEFAULT_VALUE}}';",
            templateReplace
        );
        return template.render();
    }
}