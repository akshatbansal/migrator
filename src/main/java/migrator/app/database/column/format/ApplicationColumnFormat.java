package migrator.app.database.column.format;

public interface ApplicationColumnFormat {
    public boolean hasLength();
    public boolean hasPrecision();
    public boolean hasSign();
    public boolean hasAutoIncrement();
}