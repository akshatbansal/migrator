package migrator.ext.sql.command;

import java.util.Hashtable;
import java.util.Map;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.TableChange;
import migrator.lib.stringtemplate.MapStringTemplate;
import migrator.lib.stringtemplate.StringTemplate;

public class RenameTableCommand implements CodeCommand {
    protected TableChange change;

    public RenameTableCommand(TableChange change) {
        this.change = change;
    }

    @Override
    public String toCode() {
        Map<String, String> replaceTemplate = new Hashtable<>();
        replaceTemplate.put("TABLE", this.change.getOriginalName());
        replaceTemplate.put("NEW_TABLE", this.change.getName());

        StringTemplate template = new MapStringTemplate(
            "ALTER TABLE `{{TABLE}}` RENAME TO `{{NEW_TABLE}}`;",
            replaceTemplate
        );
        return template.render();
    }
}