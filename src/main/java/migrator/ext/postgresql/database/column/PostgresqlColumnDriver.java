package migrator.ext.postgresql.database.column;

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

public class PostgresqlColumnDriver implements DatabaseColumnDriver {
    protected JdbcConnectionDriver connectionDriver;

    public PostgresqlColumnDriver(JdbcConnectionDriver connectionDriver) {
        this.connectionDriver = connectionDriver;
    }

    @Override
    public List<Map<String, String>> getColumns(String tableName) {
        List<Map<String, String>> columns = new LinkedList<>();
        ConnectionResult<Connection> connectionResult = this.connectionDriver.connect();
        if (!connectionResult.isOk()) {
            return columns;
        }

        if (tableName.isEmpty()) {
            return columns;
        }

        String sql = "SELECT column_name, column_default, is_nullable, data_type, character_maximum_length, numeric_precision, numeric_scale FROM information_schema.COLUMNS WHERE TABLE_NAME = '" + tableName + "'";
        Connection connection = connectionResult.getConnection();
        try (
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Map<String, String> column = new Hashtable<>();
                column.put("name", rs.getString(1));
                column.put("format", this.getMysqlFormat(rs));
                column.put("defaultValue", rs.getString(2) == null ? "" : rs.getString(2));
                column.put("nullEnabled", rs.getString(3).equals("Yes") ? "true" : "false");

                columns.add(column);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return columns;
    }

    private String getLength(ResultSet rs) throws SQLException {
        String lengthInt = rs.getString(5);
        String lengthStr = rs.getString(6);
        if (lengthStr == null && lengthInt == null) {
            return "";
        }
        int stringLength = 0;
        if (lengthStr != null) {
            stringLength = Integer.parseInt(lengthStr);
        }
        int intLength = 0;
        if (lengthInt != null) {
            intLength = Integer.parseInt(lengthInt);
        }
        return Integer.toString(Math.max(stringLength, intLength));
    }

    private String getPrecision(ResultSet rs) throws SQLException {
        return rs.getString(7) == null ? "" : rs.getString(7);
    }

    private String getMysqlFormat(ResultSet rs) throws SQLException {
        String length = this.getLength(rs);
        String precision = this.getPrecision(rs);
        String result = rs.getString(4);
        if (!length.isEmpty()) {
            if (precision.isEmpty()) {
                result += "(" + length + "," + precision + ")";
            } else {
                result += "(" + length + ")";
            }
        }

        return result;
    }
}