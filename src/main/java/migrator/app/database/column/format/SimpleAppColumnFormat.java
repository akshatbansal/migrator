package migrator.app.database.column.format;

public class SimpleAppColumnFormat implements ApplicationColumnFormat {
    protected boolean autoIncrement;
    protected boolean sign;
    protected boolean length;
    protected boolean precision;

    public SimpleAppColumnFormat(boolean length, boolean precision, boolean sign, boolean autoIncrement) {
        this.length = length;
        this.precision = precision;
        this.sign = sign;
        this.autoIncrement = autoIncrement;
    }

    @Override
    public boolean hasAutoIncrement() {
        return this.autoIncrement;
    }

    @Override
    public boolean hasLength() {
        return this.length;
    }

    @Override
    public boolean hasPrecision() {
        return this.precision;
    }

    @Override
    public boolean hasSign() {
        return this.sign;
    }
}