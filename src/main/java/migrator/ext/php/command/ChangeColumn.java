package migrator.ext.php.command;

import migrator.app.code.CodeCommand;
import migrator.app.migration.model.ColumnChange;

public class ChangeColumn extends ColumnCommand implements CodeCommand {
    public ChangeColumn(ColumnChange columnChange) {
        super(columnChange);
    }

    @Override
    public String toCode() {
        String php = "";
        if (this.columnChange.hasNameChanged()) {
            php += "\t->renameColumn('" + this.columnChange.getOriginal().getName() + "', '" + this.columnChange.getName() + "')\n";
        }

        if (this.columnChange.hasFormatChanged() || this.columnChange.hasDefaultValueChanged() || this.columnChange.hasNullEnabledChanged() || this.columnChange.hasLengthChanged() || this.columnChange.hasPrecisionChanged() || this.columnChange.hasSignChanged() || this.columnChange.hasAutoIncrementChanged()) {
            String options = this.getOptions();
            php += "\t->changeColumn('" + this.columnChange.getOriginal().getName() + "', '" + this.columnChange.getFormat() + "'";
            if (!options.isEmpty()) {
                php += ", " + options;
            }
            php += ")\n";
        }

        return php;
    }
}