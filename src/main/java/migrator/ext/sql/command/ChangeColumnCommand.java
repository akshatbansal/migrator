package migrator.ext.sql.command;

import java.util.HashMap;
import java.util.Map;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.lib.stringtemplate.MapStringTemplate;
import migrator.lib.stringtemplate.StringTemplate;

public class ChangeColumnCommand implements CodeCommand {
    protected String tableName;
    protected ColumnChange change;

    public ChangeColumnCommand(String tableName, ColumnChange change) {
        this.tableName = tableName;
        this.change = change;
    }

    @Override
    public String toCode() {
        String code = "";
        if (
            this.change.hasNameChanged() ||
            this.change.hasFormatChanged() ||
            this.change.hasLengthChanged() ||
            this.change.hasPrecisionChanged() ||
            this.change.hasNullEnabledChanged() ||
            this.change.hasSignChanged() ||
            this.change.hasAutoIncrementChanged()
        ) {
            ColumnTemplate columnTemplate = new ColumnTemplate(this.change);
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
        
        if (this.change.hasDefaultValueChanged()) {
            SetDefaultCommand setDefaultCommand = new SetDefaultCommand(this.change, this.tableName);
            code += setDefaultCommand.toCode();
        }
        return code;
    }
}