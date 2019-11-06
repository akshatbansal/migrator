package migrator.app.database.format.columns;

import migrator.app.database.format.NameColumnFormatter;
import migrator.app.database.format.SimpleColumnFormat;

public class DatetimeFormat extends SimpleColumnFormat {
    public DatetimeFormat() {
        super("datetime", false, false, false, false, new NameColumnFormatter("datetime"));
    }
}