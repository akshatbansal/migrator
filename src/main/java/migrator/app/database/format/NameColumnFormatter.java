package migrator.app.database.format;

public class NameColumnFormatter implements ColumnFormatter {
    protected String name;

    public NameColumnFormatter(String name) {
        this.name = name;
    }

    @Override
    public String format(String length, String precision) {
        return this.name;
    }
}