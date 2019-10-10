package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;

public class AddColumn extends ColumnCommand implements CodeCommand {
    public AddColumn(ColumnChange columnChange) {
        super(columnChange);
    }

    @Override
    public String toCode() {
        String php = "\t->addColumn('" + this.getName() + "', '" + this.getFormat() + "'";
        String options = this.getOptions();
        if (!options.isEmpty()) {
            php += ", " + options; 
        }
        return php + ")\n";
    }

    private String getName() {
        return this.columnChange.getName();
    }

    private String getFormat() {
        return this.columnChange.getFormat();
    }
}