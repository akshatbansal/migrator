package migrator.ext.mysql.column;

import java.util.Hashtable;
import java.util.Map;

import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.lib.adapter.Adapter;

public class MysqlColumnAdapter implements Adapter<ColumnProperty, Map<String, String>> {
    protected Adapter<Map<String, String>, String> formatAdapter;

    public MysqlColumnAdapter() {
        this.formatAdapter = new MysqlFormatAdapter();
    }

    @Override
    public Map<String, String> generalize(ColumnProperty item) {
        Map<String, String> formatMap = new Hashtable<>();
        formatMap.put("format", item.getFormat());
        formatMap.put("length", item.getLength());
        formatMap.put("precision", item.getPrecision());

        String format = this.formatAdapter.generalize(formatMap);

        Map<String, String> result = new Hashtable<>();
        result.put("name", item.getName());
        result.put("format", format);
        return result;
    }

    @Override
    public ColumnProperty concretize(Map<String, String> item) {
        if (item == null) {
            return null;
        }

        Map<String, String> format = this.formatAdapter.concretize(item.get("format"));

        ColumnProperty columnProperty = new SimpleColumnProperty(
            "",
            item.get("name"),
            format.get("format"),
            item.getOrDefault("defaultValue", ""),
            item.get("nullEnabled") == "YES",
            format.get("length"),
            item.getOrDefault("sign", "true") == "true",
            format.get("precision"),
            item.getOrDefault("autoIncrement", "false") == "true"
        );
        return columnProperty;
    }
}