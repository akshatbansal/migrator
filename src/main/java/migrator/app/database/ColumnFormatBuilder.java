package migrator.app.database;

import migrator.app.database.format.ColumnFormat;
import migrator.app.database.format.ColumnFormatter;
import migrator.app.database.format.NameColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class ColumnFormatBuilder {
    protected String name;
    protected Boolean length;
    protected Boolean signed;
    protected Boolean precision;
    protected Boolean autoIncrement;
    protected ColumnFormatter formatter;

    public ColumnFormatBuilder() {
        this.name = "";
        this.length = false;
        this.signed = false;
        this.precision = false;
        this.autoIncrement = false;
        this.formatter = new NameColumnFormatter(name);
    }

    public ColumnFormatBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ColumnFormatBuilder withLength() {
        this.length = true;
        return this;
    }

    public ColumnFormatBuilder withSign() {
        this.signed = true;
        return this;
    }

    public ColumnFormatBuilder withPrecision() {
        this.precision = true;
        return this;
    }

    public ColumnFormatBuilder withAutoIncrement() {
        this.autoIncrement = true;
        return this;
    }

    public ColumnFormat build() {
        return new SimpleColumnFormat(
            this.name,
            this.length,
            this.signed,
            this.precision,
            this.autoIncrement,
            this.formatter
        );
    }
}