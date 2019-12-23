package migrator.ext.mysql.index;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import migrator.app.database.ConnectionResult;
import migrator.app.database.index.DatabaseIndexDriver;
import migrator.ext.mysql.MysqlConnection;

public class MysqlIndexDriver implements DatabaseIndexDriver {
    protected MysqlConnection mysqlConnection;

    public MysqlIndexDriver(MysqlConnection mysqlConnection) {
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

        Connection connection = connectionResult.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SHOW INDEX from " + tableName;
            ResultSet rs = statement.executeQuery(sql);
            Map<String, List<String>> indexColumnsMap = new LinkedHashMap<>();
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