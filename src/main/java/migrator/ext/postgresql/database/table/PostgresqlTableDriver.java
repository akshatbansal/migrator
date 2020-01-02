package migrator.ext.postgresql.database.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import migrator.app.database.ConnectionResult;
import migrator.app.database.JdbcConnectionDriver;
import migrator.app.database.table.DatabaseTableDriver;

public class PostgresqlTableDriver implements DatabaseTableDriver {
    protected JdbcConnectionDriver connectionDriver;

    public PostgresqlTableDriver(JdbcConnectionDriver connectionDriver) {
        this.connectionDriver = connectionDriver;
    }

    @Override
    public List<String> getTables() {
        List<String> tables = new LinkedList<>();
        ConnectionResult<Connection> connectionResult = this.connectionDriver.connect();
        if (!connectionResult.isOk()) {
            return tables;
        }

        Connection connection = connectionResult.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' AND schemaname != 'information_schema';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                tables.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        this.connectionDriver.disconnect(connection);

        return tables;
    }
}