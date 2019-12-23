package migrator.ext.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import migrator.app.database.ConnectionResult;
import migrator.app.database.DatabaseConnectDriver;
import migrator.lib.logger.Logger;

public class MysqlConnection implements DatabaseConnectDriver<Connection> {
    protected String url;
    protected String user;
    protected String password;
    protected Logger logger;

    public MysqlConnection(String url, String user, String password, Logger logger) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.logger = logger;
    }

    public ConnectionResult<Connection> connect() {
        String error = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:" + this.url, this.user, this.password);
            return new ConnectionResult<>(connection);

        } catch (SQLException ex) {
            error = "Cannot connect to " + this.url + ". Reason: " + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            error = "Cannot connect to " + this.url + ". Reason: " + ex.getMessage();
        }
        return new ConnectionResult<>(error);
    }

    public void disconnect(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            this.logger.error(ex);
        }
    }
}