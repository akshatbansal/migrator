package migrator.ext.sql.command;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexChange;
import migrator.lib.stringtemplate.MapStringTemplate;
import migrator.lib.stringtemplate.StringTemplate;

public class CreateIndexCommand implements CodeCommand {
    protected String tableName;
    protected IndexChange change;

    public CreateIndexCommand(String tableName, IndexChange change) {
        this.tableName = tableName;
        this.change = change;
    }

    @Override
    public String toCode() {
        Map<String, String> templateReplace = new Hashtable<>();
        templateReplace.put("INDEX_NAME", this.change.getName());
        templateReplace.put("TABLE_NAME", this.tableName);
        List<String> columns = new LinkedList<>();
        for (ColumnProperty columnProperty : this.change.columnsProperty()) {
            columns.add(columnProperty.getName());
        }
        templateReplace.put("COLUMNS", String.join(", ", columns));
        StringTemplate template = new MapStringTemplate(
            "CREATE INDEX {{INDEX_NAME}} ON {{TABLE_NAME}}({{COLUMNS}});",
            templateReplace
        );
        return template.render();
    }
}