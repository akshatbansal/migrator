package migrator.app.database.format.columns;

import migrator.app.database.format.NameColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class DateFormat extends SimpleColumnFormat {
    public DateFormat() {
        super("date", false, false, false, false, new NameColumnFormatter("date"));
    }
}