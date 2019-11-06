package migrator.app.database.format.columns;

import migrator.app.database.format.LengthColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class CharFormat extends SimpleColumnFormat {
    public CharFormat() {
        super("char", true, false, false, false, new LengthColumnFormatter("char"));
    }
}