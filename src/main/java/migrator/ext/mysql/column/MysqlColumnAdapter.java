package migrator.ext.mysql.column;

import java.util.Map;

import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.lib.adapter.Adapter;

public class MysqlColumnAdapter implements Adapter<Map<String, String>, ColumnProperty> {
    protected Adapter<Map<String, String>, String> formatAdapter;

    public MysqlColumnAdapter() {
        this.formatAdapter = new MysqlFormatAdapter();
    }

    @Override
    public Map<String, String> concretize(ColumnProperty item) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ColumnProperty generalize(Map<String, String> item) {
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