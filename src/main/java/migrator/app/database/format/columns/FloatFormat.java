package migrator.app.database.format.columns;

import migrator.app.database.format.PrecisionColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class FloatFormat extends SimpleColumnFormat {
    public FloatFormat() {
        super("float", true, true, true, new PrecisionColumnFormatter("float"));
    }
}