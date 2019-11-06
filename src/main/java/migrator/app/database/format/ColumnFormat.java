package migrator.app.database.format;

public interface ColumnFormat {
    public String getName();
    public Boolean hasLength();
    public Boolean isSigned();
    public Boolean hasPrecision();
    public ColumnFormatter getFormatter();
    public Boolean hasAutoIncrement();
}