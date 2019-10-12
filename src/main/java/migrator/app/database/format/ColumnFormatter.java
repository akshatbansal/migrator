package migrator.app.database.format;

public interface ColumnFormatter {
    public String format(String length, String precision);
}