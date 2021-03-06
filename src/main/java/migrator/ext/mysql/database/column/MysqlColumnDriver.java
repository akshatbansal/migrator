package migrator.ext.mysql.database.column;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import migrator.app.database.ConnectionResult;
import migrator.app.database.JdbcConnectionDriver;
import migrator.app.database.column.DatabaseColumnDriver;

public class MysqlColumnDriver implements DatabaseColumnDriver {
    protected JdbcConnectionDriver mysqlConnection;

    public MysqlColumnDriver(JdbcConnectionDriver mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }

    @Override
    public List<Map<String, String>> getColumns(String tableName) {
        List<Map<String, String>> columns = new LinkedList<>();
        ConnectionResult<Connection> connectionResult = this.mysqlConnection.connect();
        if (!connectionResult.isOk()) {
            return columns;
        }

        if (tableName.isEmpty()) {
            return columns;
        }

        String sql = "DESCRIBE " + tableName;
        Connection connection = connectionResult.getConnection();
        try (
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Map<String, String> column = new Hashtable<>();
                column.put("name", rs.getString(1));
                column.put("format", rs.getString(2));
                column.put("defaultValue", rs.getString(5) == null ? "" : rs.getString(5));
                column.put("nullEnabled", rs.getString(3).equals("Yes") ? "true" : "false");

                columns.add(column);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return columns;
    }
} 