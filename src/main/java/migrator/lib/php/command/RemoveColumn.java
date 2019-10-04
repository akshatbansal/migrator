package migrator.lib.php.command;

import migrator.migration.ColumnChange;

public class RemoveColumn implements PhpCommand {
    protected ColumnChange columnChange;

    public RemoveColumn(ColumnChange columnChange) {
        this.columnChange = columnChange;
    }

    public String toPhp() {
        return "\t->removeColumn('" + this.getName() + "')\n";
    }

    private String getName() {
        return this.columnChange.getName();
    }
}