package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.IndexChange;

public class RemoveIndexByName implements CodeCommand {
    protected IndexChange indexChange;

    public RemoveIndexByName(IndexChange indexChange) {
        this.indexChange = indexChange;
    }

    @Override
    public String toCode() {
        return "\t->removeIndexByName('" + this.indexChange.getName() + "')\n";
    }
}