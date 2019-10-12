package migrator.app.database.format.columns;

import migrator.app.database.format.LengthColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class StringFormat extends SimpleColumnFormat {
    public StringFormat() {
        super("string", true, false, false, new LengthColumnFormatter("string"));
    }
}