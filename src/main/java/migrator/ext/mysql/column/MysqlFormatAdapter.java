package migrator.ext.mysql.column;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import migrator.lib.adapter.Adapter;

public class MysqlFormatAdapter implements Adapter<Map<String, String>, String> {
    private Map<String, String> formatMappingStart;
    private List<String> formatMappingEquals;

    public MysqlFormatAdapter() {
        this.formatMappingStart = new Hashtable<>();
        this.formatMappingStart.put("varchar", "string");
        this.formatMappingStart.put("int", "integer");
        this.formatMappingStart.put("tinyint", "boolean");
        this.formatMappingStart.put("double", "decimal");
        this.formatMappingStart.put("bigint", "long");
        this.formatMappingStart.put("char", "char");
        this.formatMappingStart.put("float", "float");

        this.formatMappingEquals = new LinkedList<>();
        this.formatMappingEquals.add("timestamp");
        this.formatMappingEquals.add("date");
        this.formatMappingEquals.add("datetime");
        this.formatMappingEquals.add("time");
        this.formatMappingEquals.add("text");
    }

    @Override
    public Map<String, String> concretize(String item) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        Map<String, String> result = new Hashtable<>();
        
        String format = "";
        Iterator<Entry<String, String>> iterator = this.formatMappingStart.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            if (!item.startsWith(entry.getKey())) {
                continue;
            }
            format = entry.getValue();
            break;
        }
        if (format.isEmpty()) {
            for (String formatEquals : this.formatMappingEquals) {
                if (!formatEquals.equals(item)) {
                    continue;
                }
                format = item;
                break;
            }
        }

        result.put("format", format);
        result.put("length", this.getLength(item));
        result.put("precision", this.getPrecision(item));

        return result;
    }

    @Override
    public String generalize(Map<String, String> item) {
        
        return null;
    }

    private String getLength(String dbFormat) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(dbFormat);
        if (m.find()) {
            return m.group(0);
        }
        
        return "";
    }

    private String getPrecision(String dbFormat) {
        Pattern p = Pattern.compile(",(\\d+)");
        Matcher m = p.matcher(dbFormat);
        if (m.find()) {
            return m.group(1);
        }
        
        return "";
    }
}