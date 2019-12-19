package migrator.ext.mysql.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import migrator.app.database.DatabaseTableDriver;
import migrator.ext.mysql.MysqlConnection;

public class MysqlTableDriver implements DatabaseTableDriver {
    private MysqlConnection mysqlConnection;

    public MysqlTableDriver(MysqlConnection mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }

    @Override
    public List<String> getTables() {
        List<String> tables = new LinkedList<>();
        Connection connection = this.mysqlConnection.connect();
        if (connection == null) {
            return tables;
        }
        
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