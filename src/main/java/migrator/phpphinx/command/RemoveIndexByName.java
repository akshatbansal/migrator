package migrator.phpphinx.command;

import migrator.migration.IndexChange;

public class RemoveIndexByName implements PhpCommand {
    protected IndexChange indexChange;

    public RemoveIndexByName(IndexChange indexChange) {
        this.indexChange = indexChange;
    }

    public String toPhp() {
        return "\t->removeIndexByName('" + this.indexChange.getName() + "')\n";
    }
}