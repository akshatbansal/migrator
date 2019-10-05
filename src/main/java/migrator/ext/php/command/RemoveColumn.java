package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;

public class RemoveColumn implements CodeCommand {
    protected ColumnChange columnChange;

    public RemoveColumn(ColumnChange columnChange) {
        this.columnChange = columnChange;
    }

    public String toCode() {
        return "\t->removeColumn('" + this.getName() + "')\n";
    }

    private String getName() {
        return this.columnChange.getName();
    }
}