package migrator.app.database.format;

public class LengthColumnFormatter extends NameColumnFormatter {
    public LengthColumnFormatter(String name) {
        super(name);
    }
    @Override
    public String format(String length, String precision) {
        return this.name + "(" + length + ")";
    }
}