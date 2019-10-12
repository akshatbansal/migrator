package migrator.app.database.format;

public class SimpleColumnFormat implements ColumnFormat {
    protected String name;
    protected Boolean length;
    protected Boolean signed;
    protected Boolean precision;
    protected ColumnFormatter formatter;

    public SimpleColumnFormat(
        String name,
        Boolean length,
        Boolean signed,
        Boolean precision,
        ColumnFormatter formatter
    ) {
        this.name = name;
        this.length = length;
        this.signed = signed;
        this.precision = precision;
        this.formatter = formatter;
    }

    public SimpleColumnFormat(String name) {
        this(name, false, false, false, new NameColumnFormatter(name));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boolean hasLength() {
        return this.length;
    }

    @Override
    public Boolean hasPrecision() {
        return this.precision;
    }

    @Override
    public Boolean isSigned() {
        return this.signed;
    }

    @Override
    public ColumnFormatter getFormatter() {
        return this.formatter;
    }
}