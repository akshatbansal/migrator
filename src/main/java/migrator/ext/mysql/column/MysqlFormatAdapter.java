package migrator.ext.mysql.column;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import migrator.lib.adapter.Adapter;

public class MysqlFormatAdapter implements Adapter<Map<String, String>, String> {
    private List<FormatType> formatTypes;

    public MysqlFormatAdapter() {
        this.formatTypes = new LinkedList<>();
        this.formatTypes.add(new SimpleFormatType("time", "time"));
        this.formatTypes.add(new SimpleFormatType("datetime", "datetime"));
        this.formatTypes.add(new SimpleFormatType("timestamp", "timestamp"));
        this.formatTypes.add(new SimpleFormatType("date", "date"));
        this.formatTypes.add(new SimpleFormatType("text", "text"));

        this.formatTypes.add(new LengthFormatType("varchar", "string"));
        this.formatTypes.add(new LengthFormatType("char", "char"));
        this.formatTypes.add(new LengthFormatType("int", "integer"));
        this.formatTypes.add(new LengthFormatType("tinyint", "boolean"));
        this.formatTypes.add(new LengthFormatType("bigint", "long"));

        this.formatTypes.add(new PrecisionFormatType("float", "float"));
        this.formatTypes.add(new PrecisionFormatType("double", "decimal"));
    }

    @Override
    public Map<String, String> concretize(String item) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        Map<String, String> result = new Hashtable<>();
        result.put("format", "");
        result.put("length", "");
        result.put("precision", "");
        
        for (FormatType formatType : this.formatTypes) {
            if (!formatType.matchesConcretize(item)) {
                continue;
            }

            List<String> appFormat = formatType.concretize(item);
            result.put("format", appFormat.get(0));
            result.put("length", appFormat.get(1));
            result.put("precision", appFormat.get(2));
        }

        return result;
    }

    @Override
    public String generalize(Map<String, String> item) {
        List<String> appFormat = new LinkedList<>();
        appFormat.add(item.get("format"));
        appFormat.add(item.get("length"));
        appFormat.add(item.get("precision"));

        for (FormatType formatType : this.formatTypes) {
            if (!formatType.matchesGeneralize(appFormat.get(0))) {
                continue;
            }
            return formatType.generalize(appFormat);
        }
        
        return "";
    }
}