package migrator.ext.php.command;

import java.util.ArrayList;
import java.util.List;

import migrator.app.migration.model.ColumnChange;

public class ColumnCommand {
    protected ColumnChange columnChange;

    public ColumnCommand(ColumnChange columnChange) {
        this.columnChange = columnChange;
    }

    protected String getOptions() {
        // TODO: after
        // TODO: comment
        List<String> options = new ArrayList<>();
        if (this.columnChange.isNullEnabled() != null) {
            options.add(
                "'null' => " + this.columnChange.isNullEnabled()
            );
        }
        if (this.columnChange.getDefaultValue() != null) {
            options.add(
                "'default' => '" + this.columnChange.getDefaultValue() + "'"
            );
        }
        if (this.columnChange.hasPrecisionAttribute()) {
            options.add("'precision' => " + this.columnChange.getLength());
            options.add("'scale' => " + this.columnChange.getPrecision());
        } else if (this.columnChange.hasLengthAttribute()) {
            options.add("'length' => " + this.columnChange.getLength());
        }

        if (this.columnChange.hasSignAttribute() && this.columnChange.isSigned() != null) {
            options.add("'signed' => " + this.columnChange.isSigned());
        }
        if (options.size() == 0) {
            return "";
        }
        return "[" + String.join(", ", options) + "]";
    }
}