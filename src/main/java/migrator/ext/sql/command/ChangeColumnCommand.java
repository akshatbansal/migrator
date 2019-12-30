package migrator.ext.sql.command;

import java.util.HashMap;
import java.util.Map;

import migrator.app.code.CodeCommand;
import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.domain.column.ColumnPropertyDiff;
import migrator.app.migration.model.ColumnChange;
import migrator.lib.stringtemplate.MapStringTemplate;
import migrator.lib.stringtemplate.StringTemplate;

public class ChangeColumnCommand implements CodeCommand {
    protected String tableName;
    protected ColumnChange change;
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;

    public ChangeColumnCommand(String tableName, ColumnChange change, ApplicationColumnFormatCollection applicationColumnFormatCollection) {
        this.applicationColumnFormatCollection = applicationColumnFormatCollection;
        this.tableName = tableName;
        this.change = change;
    }

    @Override
    public String toCode() {
        ApplicationColumnFormat appFormat = this.applicationColumnFormatCollection.getFormatByName(this.change.getFormat());
        ColumnPropertyDiff columnDiff = new ColumnPropertyDiff(this.change.getOriginal(), this.change, appFormat);
        String code = "";
        if (columnDiff.hasChangedExcept("defaultValue")) {
            ColumnTemplate columnTemplate = new ColumnTemplate(this.change, appFormat);
            Map<String, String> templateVars = new HashMap<>();
            templateVars.put("TABLE", this.tableName);
            templateVars.put("COLUMN", this.change.getOriginal().getName());
            templateVars.put("NEW_COLUMN", columnTemplate.render());
            StringTemplate template = new MapStringTemplate(
                "ALTER TABLE `{{TABLE}}` CHANGE `{{COLUMN}}` {{NEW_COLUMN}};",
                templateVars
            );
            code += template.render();
        }
        
        if (columnDiff.hasChanged("defaultValue")) {
            SetDefaultCommand setDefaultCommand = new SetDefaultCommand(this.change, this.tableName);
            code += setDefaultCommand.toCode();
        }
        return code;
    }
}