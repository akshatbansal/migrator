package migrator.phpphinx.command;

import migrator.migration.ColumnChange;

public class AddColumn implements PhpCommand {
    protected ColumnChange columnChange;

    public AddColumn(ColumnChange columnChange) {
        this.columnChange = columnChange;
    }

    public String toPhp() {
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

    private String getOptions() {
        // limit
        // after
        // null
        // default
        // comment
        String options = "";
        if (this.columnChange.isNullEnabled() != null) {
            options = "'null' => " + this.columnChange.isNullEnabled();
        }
        return "[" + options + "]";
    }
}