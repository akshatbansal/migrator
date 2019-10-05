package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;

public class ChangeColumn implements CodeCommand {
    protected ColumnChange columnChange;

    public ChangeColumn(ColumnChange columnChange) {
        this.columnChange = columnChange;
    }

    @Override
    public String toCode() {
        String php = "";
        if (this.columnChange.hasNameChanged()) {
            php += "\t->renameColumn('" + this.columnChange.getOriginalName() + "', '" + this.columnChange.getName() + "')\n";
        }

        return php;
    }
}