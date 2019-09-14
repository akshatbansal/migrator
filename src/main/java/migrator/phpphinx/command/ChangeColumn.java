package migrator.phpphinx.command;

import migrator.migration.ColumnChange;

public class ChangeColumn implements PhpCommand {
    protected ColumnChange columnChange;

    public ChangeColumn(ColumnChange columnChange) {
        this.columnChange = columnChange;
    }

    public String toPhp() {
        String php = "";
        if (this.columnChange.getCommand().hasArgument("name")) {
            php += "\t->renameColumn('" + this.columnChange.getName() + "', '" + this.columnChange.getCommand().getArgument("name") + "')\n";
        }

        return php;
    }
}