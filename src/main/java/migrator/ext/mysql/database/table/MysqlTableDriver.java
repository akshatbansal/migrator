package migrator.ext.mysql.database.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import migrator.app.database.ConnectionResult;
import migrator.app.database.JdbcConnectionDriver;
import migrator.app.database.table.DatabaseTableDriver;

public class MysqlTableDriver implements DatabaseTableDriver {
    private JdbcConnectionDriver mysqlConnection;

    public MysqlTableDriver(JdbcConnectionDriver mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }

    @Override
    public List<String> getTables() {
        List<String> tables = new LinkedList<>();
        ConnectionResult<Connection> connectionResult = this.mysqlConnection.connect();
        if (!connectionResult.isOk()) {
            return tables;
        }

        Connection connection = connectionResult.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SHOW TABLES";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tables;
    }
} 