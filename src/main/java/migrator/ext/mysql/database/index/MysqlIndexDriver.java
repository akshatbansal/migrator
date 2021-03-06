package migrator.ext.mysql.database.index;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import migrator.app.database.ConnectionResult;
import migrator.app.database.JdbcConnectionDriver;
import migrator.app.database.index.DatabaseIndexDriver;

public class MysqlIndexDriver implements DatabaseIndexDriver {
    protected JdbcConnectionDriver mysqlConnection;

    public MysqlIndexDriver(JdbcConnectionDriver mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }

    @Override
    public List<List<String>> getIndexes(String tableName) {
        List<List<String>> indexes = new ArrayList<>();
        ConnectionResult<Connection> connectionResult = this.mysqlConnection.connect();
        if (!connectionResult.isOk()) {
            return indexes;
        }

        if (tableName.isEmpty()) {
            return indexes;
        }

        String sql = "SHOW INDEX from " + tableName;
        Connection connection = connectionResult.getConnection();
        try (
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)
        ) {
            Map<String, List<String>> indexColumnsMap = new Hashtable<>();
            while (rs.next()) {
                String indexName = rs.getString(3);
                if (!indexColumnsMap.containsKey(indexName)) {
                    indexColumnsMap.put(indexName, new ArrayList<>());
                }
                indexColumnsMap.get(indexName).add(rs.getString(5));
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