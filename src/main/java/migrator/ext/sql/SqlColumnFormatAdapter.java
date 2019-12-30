package migrator.ext.sql;

import java.util.HashMap;
import java.util.Map;

import migrator.app.migration.model.ColumnProperty;
import migrator.ext.mysql.column.MysqlFormatAdapter;
import migrator.lib.adapter.Adapter;

public class SqlColumnFormatAdapter implements Adapter<ColumnProperty, String> {
    protected MysqlFormatAdapter mysqlFormatAdapter;

    public SqlColumnFormatAdapter() {
        this.mysqlFormatAdapter = new MysqlFormatAdapter();
    }

    @Override
    public ColumnProperty concretize(String item) {
        return null;
    }

    @Override
    public String generalize(ColumnProperty item) {
        Map<String, String> formatMap = new HashMap<>();
        formatMap.put("format", item.getFormat());
        formatMap.put("length", item.getLength());
        formatMap.put("precision", item.getPrecision());

        return this.mysqlFormatAdapter.generalize(formatMap);
    }
}