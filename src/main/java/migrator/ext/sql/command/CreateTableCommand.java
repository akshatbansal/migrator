package migrator.ext.sql.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import migrator.app.code.CodeCommand;
import migrator.app.code.CommandSequence;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.lib.stringtemplate.MapStringTemplate;
import migrator.lib.stringtemplate.StringTemplate;

public class CreateTableCommand implements CodeCommand {
    protected TableChange change;
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;

    public CreateTableCommand(TableChange change, ApplicationColumnFormatCollection applicationColumnFormatCollection) {
        this.change = change;
        this.applicationColumnFormatCollection = applicationColumnFormatCollection;
    }

    @Override
    public String toCode() {

        Map<String, String> templateReplace = new HashMap<>();
        templateReplace.put("NAME", this.change.getName());
        List<String> columns = new LinkedList<>();
        for (ColumnChange columnChange : this.change.getColumnsChanges()) {
            columns.add(this.columnChangeCode(columnChange));
        }
        templateReplace.put("COLUMNS", String.join(", ", columns));

        List<CodeCommand> indexCommands = new LinkedList<>();
        for (IndexChange indexChange : this.change.getIndexesChanges()) {
            indexCommands.add(
                new CreateIndexCommand(this.change.getName(), indexChange)
            );
        }
        CommandSequence indexSequence = new CommandSequence(indexCommands);
        templateReplace.put("INDEXES", indexSequence.toCode());

        StringTemplate createTableTemplate = new MapStringTemplate(
            "CREATE TABLE IF NOT EXISTS `{{NAME}}` ({{COLUMNS}});{{INDEXES}}",
            templateReplace
        );
        return createTableTemplate.render();
    }

    protected String columnChangeCode(ColumnChange columnChange) {
        StringTemplate template = new ColumnTemplate(columnChange, this.applicationColumnFormatCollection.getFormatByName(columnChange.getFormat()));
        return template.render();
    }
}