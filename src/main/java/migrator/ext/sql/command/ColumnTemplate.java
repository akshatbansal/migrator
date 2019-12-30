package migrator.ext.sql.command;

import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.migration.model.ColumnChange;
import migrator.ext.sql.SqlColumnFormatAdapter;
import migrator.lib.stringtemplate.StringTemplate;

public class ColumnTemplate implements StringTemplate {
    protected ColumnChange change;
    protected ApplicationColumnFormat columnFormat;

    public ColumnTemplate(ColumnChange change, ApplicationColumnFormat columnFormat) {
        this.change = change;
        this.columnFormat = columnFormat;
    }

    @Override
    public String render() {
        SqlColumnFormatAdapter adapter = new SqlColumnFormatAdapter();
        String column = "`" + this.change.getName() + "` " + adapter.generalize(change);
        if (this.columnFormat.hasSign() && !this.change.isSigned()) {
            column += " UNSIGNED";
        }
        if (this.columnFormat.hasAutoIncrement() && this.change.isAutoIncrement()) {
            column += " auto_increment primary key";
        }
        if (!this.change.isNullEnabled()) {
            column += " NOT NULL";
        }
        if (this.change.getDefaultValue() != null && !this.change.getDefaultValue().isEmpty()) {
            column += " DEFAULT " + this.change.getDefaultValue();
        }
        return column;
    }
}