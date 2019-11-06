package migrator.app.database.format.columns;

import migrator.app.database.format.LengthColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class LongFormat extends SimpleColumnFormat {
    public LongFormat() {
        super("long", true, true, false, true, new LengthColumnFormatter("long"));
    }
}