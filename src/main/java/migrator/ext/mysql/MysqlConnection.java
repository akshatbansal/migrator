package migrator.ext.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import migrator.lib.logger.Logger;

public class MysqlConnection {
    protected String url;
    protected String user;
    protected String password;
    protected Logger logger;

    public MysqlConnection(String url, String user, String password, Logger logger) {
        this.logger = logger;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:" + this.url, this.user, this.password);
        } catch (SQLException ex) {
            this.logger.info("Cannot connect to " + this.url + ". Reason: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            this.logger.error("Cannot connect to " + this.url + ". Reason: " + ex.getMessage());
        }
        return null;
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