package migrator.ext.php.command;

import java.util.ArrayList;
import java.util.List;

import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.migration.model.ColumnChange;

public class ColumnCommand {
    protected ColumnChange columnChange;
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;

    public ColumnCommand(ColumnChange columnChange, ApplicationColumnFormatCollection applicationColumnFormatCollection) {
        this.columnChange = columnChange;
        this.applicationColumnFormatCollection = applicationColumnFormatCollection;
    }

    protected String getOptions() {
        // TODO: after
        // TODO: comment
        ApplicationColumnFormat format = this.applicationColumnFormatCollection.getFormatByName(this.columnChange.getFormat());
        List<String> options = new ArrayList<>();
        if (this.columnChange.isNullEnabled() != null) {
            options.add(
                "'null' => " + this.columnChange.isNullEnabled()
            );
        }
        if (this.columnChange.getDefaultValue() != null && !this.columnChange.getDefaultValue().isEmpty()) {
            options.add(
                "'default' => '" + this.columnChange.getDefaultValue() + "'"
            );
        }
        if (format.hasPrecision()) {
            options.add("'precision' => " + this.columnChange.getLength());
            options.add("'scale' => " + this.columnChange.getPrecision());
        } else if (format.hasLength()) {
            options.add("'length' => " + this.columnChange.getLength());
        }

        if (format.hasSign()) {
            options.add("'signed' => " + this.columnChange.isSigned());
        }

        if (format.hasAutoIncrement() && this.columnChange.isAutoIncrement()) {
            options.add("'identity' => " + this.columnChange.isAutoIncrement());
        }

        if (options.size() == 0) {
            return "";
        }
        return "[" + String.join(", ", options) + "]";
    }
}