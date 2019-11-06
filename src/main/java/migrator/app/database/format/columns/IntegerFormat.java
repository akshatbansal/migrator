package migrator.app.database.format.columns;

import migrator.app.database.format.LengthColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class IntegerFormat extends SimpleColumnFormat {
    public IntegerFormat() {
        super("integer", true, true, false, true, new LengthColumnFormatter("integer"));
    }
}