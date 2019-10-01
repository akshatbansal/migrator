package migrator.phpphinx.command;

import migrator.migration.ColumnChange;

public class ChangeColumn implements PhpCommand {
    protected ColumnChange columnChange;

    public ChangeColumn(ColumnChange columnChange) {
        this.columnChange = columnChange;
    }

    public String toPhp() {
        String php = "";
        if (this.columnChange.hasNameChanged()) {
            php += "\t->renameColumn('" + this.columnChange.getOriginalName() + "', '" + this.columnChange.getName() + "')\n";
        }

        return php;
    }
}