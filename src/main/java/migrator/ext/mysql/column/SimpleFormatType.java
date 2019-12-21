package migrator.ext.mysql.column;

import java.util.LinkedList;
import java.util.List;

public class SimpleFormatType implements FormatType {
    protected String dbName;
    protected String appName;

    public SimpleFormatType(String dbName, String appName) {
        this.dbName = dbName;
        this.appName = appName;
    }

    @Override
    public boolean matchesConcretize(String format) {
        return this.dbName.equals(format);
    }

    @Override
    public boolean matchesGeneralize(String format) {
        return this.appName.equals(format);
    }

    @Override
    public List<String> concretize(String item) {
        List<String> result = new LinkedList<>();
        result.add(this.appName);
        result.add("");
        result.add("");
        return result;
    }

    @Override
    public String generalize(List<String> item) {
        return this.dbName;
    }
}