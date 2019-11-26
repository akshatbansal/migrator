package migrator.ext.sql.command;

import java.util.Hashtable;
import java.util.Map;

import migrator.app.migration.model.ColumnChange;
import migrator.lib.stringtemplate.MapStringTemplate;
import migrator.lib.stringtemplate.StringTemplate;

public class ColumnTemplate implements StringTemplate {
    protected ColumnChange change;

    public ColumnTemplate(ColumnChange change) {
        this.change = change;
    }

    @Override
    public String render() {
        String column = "`" + this.change.getName() + "` " + this.columnChangeFormat(this.change);
        if (this.change.hasSignAttribute() && !this.change.isSigned()) {
            column += " UNSIGNED";
        }
        if (this.change.hasAutoIncrementAttribute() && this.change.isAutoIncrement()) {
            column += " auto_increment";
        }
        if (!this.change.isNullEnabled()) {
            column += " NOT NULL";
        }
        return column;
    }

    protected String columnChangeFormat(ColumnChange columnChange) {
        String templateString = "{{FORMAT}}";
        Map<String, String> templateReplace = new Hashtable<>();
        templateReplace.put("FORMAT", this.getSqlFormat(columnChange.getFormat()));
        if (columnChange.hasPrecisionAttribute()) {
            templateReplace.put("LENGTH", columnChange.getLength());
            templateReplace.put("PRECISION", columnChange.getPrecision());
            templateString = "{{FORMAT}}({{LENGTH}}, {{PRECISION}})";
        } else if (columnChange.hasLengthAttribute()) {
            templateReplace.put("LENGTH", columnChange.getLength());
            templateString = "{{FORMAT}}({{LENGTH}})";
        }
        StringTemplate template = new MapStringTemplate(
            templateString,
            templateReplace
        );
        return template.render();
    }

    protected String getSqlFormat(String format) {
        if (format.equals("string")) {
            return "varchar";
        } else if (format.equals("boolean")) {
            return "tinyint(1)";
        } else if (format.equals("integer")) {
            return "int";
        } else if (format.equals("long")) {
            return "bigint";
        }
        return format;
    }
}