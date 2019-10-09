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
        // limit
        // after
        // null
        // comment
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
        if (options.size() == 0) {
            return "";
        }
        return "[" + String.join(", ", options) + "]";
    }
}