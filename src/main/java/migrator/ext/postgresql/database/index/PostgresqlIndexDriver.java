package migrator.ext.postgresql.database.index;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import migrator.app.database.ConnectionResult;
import migrator.app.database.JdbcConnectionDriver;
import migrator.app.database.index.DatabaseIndexDriver;

public class PostgresqlIndexDriver implements DatabaseIndexDriver {
    protected JdbcConnectionDriver connectionDriver;

    public PostgresqlIndexDriver(JdbcConnectionDriver connectionDriver) {
        this.connectionDriver = connectionDriver;
    }

    @Override
    public List<List<String>> getIndexes(String tableName) {
        List<List<String>> indexes = new LinkedList<>();
        ConnectionResult<Connection> connectionResult = this.connectionDriver.connect();
        if (!connectionResult.isOk()) {
            return indexes;
        }

        if (tableName.isEmpty()) {
            return indexes;
        }

        Connection connection = connectionResult.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "select i.relname as index_name, a.attname as column_name from pg_class t, pg_class i, pg_index ix, pg_attribute a where t.oid = ix.indrelid and i.oid = ix.indexrelid and a.attrelid = t.oid and a.attnum = ANY(ix.indkey) and t.relkind = 'r' and t.relname like '" + tableName + "' order by t.relname, i.relname;";
            ResultSet rs = statement.executeQuery(sql);
            Map<String, List<String>> indexColumnsMap = new LinkedHashMap<>();
            while (rs.next()) {
                String indexName = rs.getString(1);
                if (!indexColumnsMap.containsKey(indexName)) {
                    indexColumnsMap.put(indexName, new LinkedList<>());
                }
                indexColumnsMap.get(indexName).add(rs.getString(2));
            }
            Iterator<Entry<String, List<String>>> entryIterator = indexColumnsMap.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Entry<String, List<String>> entry = entryIterator.next();
                entry.getValue().add(0, entry.getKey());
                indexes.add(entry.getValue());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return indexes;
    }
}