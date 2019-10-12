package migrator.app.database.format;

public class PrecisionColumnFormatter extends NameColumnFormatter {
    public PrecisionColumnFormatter(String name) {
        super(name);
    }

    @Override
    public String format(String length, String precision) {
        return this.name + "(" + length + ", " + precision + ")";
    }
}