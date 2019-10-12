package migrator.app.database.format.columns;

import migrator.app.database.format.PrecisionColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class DecimalFormat extends SimpleColumnFormat {
    public DecimalFormat() {
        super("decimal", true, true, true, new PrecisionColumnFormatter("decimal"));
    }
}