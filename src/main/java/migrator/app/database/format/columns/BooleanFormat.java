package migrator.app.database.format.columns;

import migrator.app.database.format.NameColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class BooleanFormat extends SimpleColumnFormat {
    public BooleanFormat() {
        super("boolean", false, false, false, false, new NameColumnFormatter("boolean"));
    }
}