package migrator.ext.sql;

import java.util.HashMap;
import java.util.Map;

import migrator.app.migration.model.ColumnProperty;
import migrator.ext.sql.database.column.SqlFormatAdapter;
import migrator.lib.adapter.Adapter;

public class SqlColumnFormatAdapter implements Adapter<ColumnProperty, String> {
    protected SqlFormatAdapter sqlFormatAdapter;

    public SqlColumnFormatAdapter() {
        this.sqlFormatAdapter = new SqlFormatAdapter();
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

        return this.sqlFormatAdapter.generalize(formatMap);
    }
}