package migrator.ext.phinx;

import java.text.SimpleDateFormat;
import java.util.Date;

import migrator.lib.stringformatter.StringFormatter;

public class TimestampFileNameFormatter implements StringFormatter {

    public TimestampFileNameFormatter() {}

    @Override
    public String format(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        return timestamp + "_" + input;
    }
}